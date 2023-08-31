package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.ImmutableMap;

/**
 * Represents a tile on the chessboard with a specific coordinate.
 */
public abstract class Tile {

    /** The coordinate of the tile on the chessboard. */
    protected final int tileCoordinate;

    // Static Fields

    /**
     * A map of all possible empty tiles on the chessboard, mapped to their coordinates.
     */
    private static final Map<Integer, emptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    // Static Methods

    /**
     * Creates and initializes all possible empty tile objects with their respective coordinates.
     *
     * @return An immutable map of empty tiles.
     */
    private static Map<Integer, emptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, emptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new emptyTile(i));
        }

        // Returns an immutable map of the empty tile map
        return ImmutableMap.copyOf(emptyTileMap);
    }

    /**
     * Creates a tile object with the given coordinate and associated piece.
     *
     * @param tileCoordinate The coordinate of the tile.
     * @param piece The piece placed on the tile, or null for an empty tile.
     * @return A new tile object.
     */
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        // If the piece is null, return an empty tile; otherwise, return an occupied tile
        return piece != null ? new OccupiedTile(tileCoordinate, piece) :
                EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    // Constructors

    /**
     * Constructs a tile object with the given coordinate.
     *
     * @param tileCoordinate The coordinate of the tile.
     */
    private Tile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    // Abstract Methods

    /**
     * Checks whether the tile is occupied by a piece.
     *
     * @return True if the tile is occupied, false otherwise.
     */
    public abstract boolean isTileOccupied();

    /**
     * Gets the piece on the tile.
     *
     * @return The piece on the tile, or null if the tile is empty.
     */
    public abstract Piece getPiece();

    // Subclasses

    /**
     * Represents an empty tile on the chessboard.
     */
    public static final class emptyTile extends Tile {

        /**
         * Constructs an empty tile object with the given coordinate.
         *
         * @param coordinate The coordinate of the empty tile.
         */
        private emptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    /**
     * Represents an occupied tile on the chessboard.
     */
    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        /**
         * Constructs an occupied tile object with the given coordinate and associated piece.
         *
         * @param tileCoordinate The coordinate of the occupied tile.
         * @param pieceOnTile The piece placed on the tile.
         */
        private OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString() {
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                getPiece().toString();
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
