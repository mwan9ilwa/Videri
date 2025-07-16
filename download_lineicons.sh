#!/bin/bash

# LineIcons SVG Download Script for Videri
# This script downloads essential LineIcons SVG files from their GitHub repository

echo "🎬 Downloading LineIcons SVG files for Videri..."

# Create the drawable directory if it doesn't exist
mkdir -p composeApp/src/commonMain/composeResources/drawable

# Base URL for LineIcons SVG files
BASE_URL="https://raw.githubusercontent.com/LineiconsHQ/Lineicons/master/src/svg"

# Array of icon names we need
declare -a icons=(
    # Navigation
    "home"
    "search"
    "library"
    "user"
    
    # Media
    "play"
    "movie"
    "television"
    "star"
    "star-filled"
    
    # Actions
    "heart"
    "heart-filled"
    "bookmark"
    "bookmark-filled"
    "plus"
    "checkmark"
    "close"
    
    # Interface
    "filter"
    "sort"
    "cog"
    "more"
    "arrow-left"
    "arrow-right"
)

# Download each icon
for icon in "${icons[@]}"
do
    echo "Downloading $icon..."
    filename="lineicons_${icon//-/_}.xml"
    
    # Download the SVG and convert to Android Vector Drawable format
    curl -s "$BASE_URL/$icon.svg" | \
    sed 's/<svg/<vector/' | \
    sed 's/<\/svg>/<\/vector>/' | \
    sed 's/width="[^"]*"/android:width="24dp"/' | \
    sed 's/height="[^"]*"/android:height="24dp"/' | \
    sed 's/viewBox="/android:viewportWidth="24" android:viewportHeight="24" android:viewBox="/' | \
    sed 's/<path/<path android:fillColor="@android:color/black"/' \
    > "composeApp/src/commonMain/composeResources/drawable/$filename"
    
    echo "✅ Downloaded $filename"
done

echo "🎉 All LineIcons downloaded successfully!"
echo "📁 Files are located in: composeApp/src/commonMain/composeResources/drawable/"
