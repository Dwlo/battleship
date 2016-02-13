#!/bin/bash -e

. ./json-parser

PLAYER="$1"
GAME_SIZE="$2"
BATTLESHIP_URL="http://localhost:3000"

get_game_id() {
    RESPONSE=$(curl -s -X POST "${BATTLESHIP_URL}/games?size=${GAME_SIZE}")
    GAME_ID=$(jsonElement "game-id" "${RESPONSE}")
}

get_game_id

for r in $(seq 0 $(($GAME_SIZE-1)))
do
    for c in $(seq 0 $(($GAME_SIZE-1)))
    do
        curl -s -X PUT "${BATTLESHIP_URL}/games/${GAME_ID}/players/${PLAYER}/fire?row=${r}&col=${c}"
    done
done

echo -e "\n\n"

curl -s "${BATTLESHIP_URL}/games/${GAME_ID}/battlefield"
echo -e "\n\nGAME-ID is ${GAME_ID}"
