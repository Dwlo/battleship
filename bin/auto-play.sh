#!/bin/bash -e

. ./json-parser

BATTLESHIP_URL="http://localhost:3000"

get_game_id() {
    RESPONSE=$(curl -s -X POST -H "Content-type: application/json" -d @game-creation.json "${BATTLESHIP_URL}/games")
    GAME_ID=$(jsonElement "game-id" "${RESPONSE}")
}

get_game_id

echo ""

GAME_SIZE=3
PLAYER="x"

for r in $(seq 0 $(($GAME_SIZE-1)))
do
    for c in $(seq 0 $(($GAME_SIZE-1)))
    do
        curl -s -X PUT "${BATTLESHIP_URL}/games/${GAME_ID}/players/${PLAYER}/fire?row=${r}&col=${c}"
    done
done > /dev/null

curl -s "${BATTLESHIP_URL}/games/${GAME_ID}/battlefield"
echo -e "\n\nGAME-ID is ${GAME_ID}"
