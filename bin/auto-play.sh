#!/bin/bash -e

. ./json-parser

PLAYER="$1"
BATTLESHIP_URL="http://localhost:3000"

function get_game_id {
    RESPONSE=$(curl -s -X POST "${BATTLESHIP_URL}/games")
    GAME_ID=$(jsonElement "game-id" "${RESPONSE}")
}


get_game_id

for r in {0..4}
do
    for c in {0..4}
    do
        curl -s -X PUT "http://localhost:3000/games/${GAME_ID}/players/${PLAYER}/fire?row=${r}&col=${c}"
    done
done

curl -s "${BATTLESHIP_URL}/games/${GAME_ID}/battlefield"
