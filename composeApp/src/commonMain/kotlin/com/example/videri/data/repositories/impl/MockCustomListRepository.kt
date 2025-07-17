package com.example.videri.data.repositories.impl

import com.example.videri.data.models.*
import com.example.videri.data.repositories.CustomListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class MockCustomListRepository : CustomListRepository {
    
    private val _userLists = MutableStateFlow<List<CustomList>>(
        // Start with some default lists
        listOf(
            CustomList(
                id = "default_watchlist",
                name = "My Watchlist",
                description = "Things I want to watch",
                isPublic = false,
                createdDate = getCurrentDate(),
                updatedDate = getCurrentDate(),
                contentIds = emptyList(),
                contentCount = 0
            ),
            CustomList(
                id = "default_favorites",
                name = "My Favorites",
                description = "My all-time favorite movies and shows",
                isPublic = false,
                createdDate = getCurrentDate(),
                updatedDate = getCurrentDate(),
                contentIds = emptyList(),
                contentCount = 0
            )
        )
    )
    
    private val _listContents = MutableStateFlow<Map<String, List<String>>>(emptyMap())
    
    companion object {
        private fun generateId(): String = Clock.System.now().toEpochMilliseconds().toString()
        private fun getCurrentDate(): String = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
    }
    
    override suspend fun createList(name: String, description: String?, isPublic: Boolean): CustomList {
        val newList = CustomList(
            id = generateId(),
            name = name,
            description = description,
            isPublic = isPublic,
            createdDate = getCurrentDate(),
            updatedDate = getCurrentDate(),
            contentIds = emptyList(),
            contentCount = 0
        )
        
        val currentLists = _userLists.value.toMutableList()
        currentLists.add(newList)
        _userLists.value = currentLists
        
        return newList
    }
    
    override suspend fun updateList(list: CustomList): CustomList {
        val currentLists = _userLists.value.toMutableList()
        val index = currentLists.indexOfFirst { it.id == list.id }
        
        if (index != -1) {
            val updatedList = list.copy(updatedDate = getCurrentDate())
            currentLists[index] = updatedList
            _userLists.value = currentLists
            return updatedList
        }
        
        return list
    }
    
    override suspend fun deleteList(listId: String) {
        val currentLists = _userLists.value.toMutableList()
        currentLists.removeAll { it.id == listId }
        _userLists.value = currentLists
        
        // Also remove content mapping
        val currentContents = _listContents.value.toMutableMap()
        currentContents.remove(listId)
        _listContents.value = currentContents
    }
    
    override suspend fun getUserLists(): Flow<List<CustomList>> = _userLists.asStateFlow()
    
    override suspend fun getListById(listId: String): CustomList? {
        return _userLists.value.find { it.id == listId }
    }
    
    override suspend fun addContentToList(listId: String, contentId: String, contentType: ContentType) {
        val currentContents = _listContents.value.toMutableMap()
        val listContents = currentContents[listId]?.toMutableList() ?: mutableListOf()
        
        val contentKey = "${contentType.name}_$contentId"
        if (!listContents.contains(contentKey)) {
            listContents.add(contentKey)
            currentContents[listId] = listContents
            _listContents.value = currentContents
            
            // Update the list's content count
            updateListContentCount(listId, listContents.size)
        }
    }
    
    override suspend fun removeContentFromList(listId: String, contentId: String, contentType: ContentType) {
        val currentContents = _listContents.value.toMutableMap()
        val listContents = currentContents[listId]?.toMutableList()
        
        listContents?.let { contents ->
            val contentKey = "${contentType.name}_$contentId"
            contents.remove(contentKey)
            currentContents[listId] = contents
            _listContents.value = currentContents
            
            // Update the list's content count
            updateListContentCount(listId, contents.size)
        }
    }
    
    private suspend fun updateListContentCount(listId: String, count: Int) {
        val currentLists = _userLists.value.toMutableList()
        val index = currentLists.indexOfFirst { it.id == listId }
        
        if (index != -1) {
            currentLists[index] = currentLists[index].copy(
                contentCount = count,
                updatedDate = getCurrentDate()
            )
            _userLists.value = currentLists
        }
    }
    
    override suspend fun getListContent(listId: String): Flow<List<Any>> {
        return kotlinx.coroutines.flow.flow {
            _listContents.collect { contentsMap ->
                val contentKeys = contentsMap[listId] ?: emptyList()
                
                // In a real implementation, you would fetch the actual content objects
                // For now, we'll return mock data
                val mockContent = contentKeys.mapNotNull { contentKey ->
                    val parts = contentKey.split("_", limit = 2)
                    if (parts.size == 2) {
                        val contentType = parts[0]
                        val contentId = parts[1]
                        
                        when (contentType) {
                            "MOVIE" -> createMockMovie(contentId)
                            "TV_SHOW" -> createMockTVShow(contentId)
                            else -> null
                        }
                    } else null
                }
                
                emit(mockContent)
            }
        }
    }
    
    private fun createMockMovie(id: String): Movie {
        val years = listOf(2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024)
        val ratings = listOf(7.0f, 7.1f, 7.2f, 7.3f, 7.4f, 7.5f, 7.6f, 7.7f, 7.8f, 7.9f, 8.0f, 8.1f, 8.2f, 8.3f, 8.4f, 8.5f, 8.6f, 8.7f, 8.8f, 8.9f, 9.0f)
        
        return Movie(
            id = id,
            title = "Mock Movie $id",
            rating = ratings.random(),
            releaseYear = years.random().toString(),
            description = "A mock movie description for movie $id",
            genres = listOf("Action", "Drama").shuffled().take(2)
        )
    }
    
    private fun createMockTVShow(id: String): TVShow {
        val years = listOf(2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024)
        val ratings = listOf(7.0f, 7.1f, 7.2f, 7.3f, 7.4f, 7.5f, 7.6f, 7.7f, 7.8f, 7.9f, 8.0f, 8.1f, 8.2f, 8.3f, 8.4f, 8.5f, 8.6f, 8.7f, 8.8f, 8.9f, 9.0f)
        val statuses = listOf("Ongoing", "Ended")
        
        return TVShow(
            id = id,
            title = "Mock TV Show $id",
            rating = ratings.random(),
            releaseYear = years.random().toString(),
            description = "A mock TV show description for show $id",
            genres = listOf("Drama", "Comedy", "Thriller").shuffled().take(2),
            status = statuses.random()
        )
    }
}
