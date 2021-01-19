package club.frozed.lib.cuboid;

import club.frozed.lib.FrozedLib;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */

public class Cuboid implements Iterable<Block>, Cloneable, ConfigurationSerializable {

    protected final String worldName;

    protected final int x1;

    protected final int y1;

    protected final int z1;

    protected final int x2;

    protected final int y2;

    protected final int z2;

    public Cuboid(Location locationOne, Location locationTwo) {
        if (!locationOne.getWorld().equals(locationTwo.getWorld()))
            throw new IllegalArgumentException("Locations must be on the same world");
        this.worldName = locationOne.getWorld().getName();
        this.x1 = Math.min(locationOne.getBlockX(), locationTwo.getBlockX());
        this.y1 = Math.min(locationOne.getBlockY(), locationTwo.getBlockY());
        this.z1 = Math.min(locationOne.getBlockZ(), locationTwo.getBlockZ());
        this.x2 = Math.max(locationOne.getBlockX(), locationTwo.getBlockX());
        this.y2 = Math.max(locationOne.getBlockY(), locationTwo.getBlockY());
        this.z2 = Math.max(locationOne.getBlockZ(), locationTwo.getBlockZ());
    }

    public Cuboid(Location l1) {
        this(l1, l1);
    }

    public Cuboid(Cuboid other) {
        this(other.getWorld().getName(), other.x1, other.y1, other.z1, other.x2, other.y2, other.z2);
    }

    public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.worldName = world.getName();
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    private Cuboid(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.worldName = worldName;
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    public Cuboid(Map<String, Object> map) {
        this.worldName = (String)map.get("worldName");
        this.x1 = ((Integer)map.get("x1")).intValue();
        this.x2 = ((Integer)map.get("x2")).intValue();
        this.y1 = ((Integer)map.get("y1")).intValue();
        this.y2 = ((Integer)map.get("y2")).intValue();
        this.z1 = ((Integer)map.get("z1")).intValue();
        this.z2 = ((Integer)map.get("z2")).intValue();
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("worldName", this.worldName);
        map.put("x1", Integer.valueOf(this.x1));
        map.put("y1", Integer.valueOf(this.y1));
        map.put("z1", Integer.valueOf(this.z1));
        map.put("x2", Integer.valueOf(this.x2));
        map.put("y2", Integer.valueOf(this.y2));
        map.put("z2", Integer.valueOf(this.z2));
        return map;
    }

    public Location getLowerNE() {
        return new Location(getWorld(), this.x1, this.y1, this.z1);
    }

    public Location getUpperSW() {
        return new Location(getWorld(), this.x2, this.y2, this.z2);
    }

    public List<Block> getBlocks() {
        Iterator<Block> blockI = iterator();
        List<Block> copy = new ArrayList<>();
        while (blockI.hasNext())
            copy.add(blockI.next());
        return copy;
    }

    public Location getCenter() {
        int x1 = getUpperX() + 1;
        int y1 = getUpperY() + 1;
        int z1 = getUpperZ() + 1;
        return new Location(getWorld(), getLowerX() + (x1 - getLowerX()) / 2.0D,
                getLowerY() + (y1 - getLowerY()) / 2.0D,
                getLowerZ() + (z1 - getLowerZ()) / 2.0D);
    }

    public World getWorld() {
        World world = FrozedLib.INSTANCE.getPlugin().getServer().getWorld(this.worldName);
        if (world == null)
            throw new IllegalStateException("World '" + this.worldName + "' is not loaded");
        return world;
    }

    public int getSizeX() {
        return this.x2 - this.x1 + 1;
    }

    public int getSizeY() {
        return this.y2 - this.y1 + 1;
    }

    public int getSizeZ() {
        return this.z2 - this.z1 + 1;
    }

    public int getLowerX() {
        return this.x1;
    }

    public int getLowerY() {
        return this.y1;
    }

    public int getLowerZ() {
        return this.z1;
    }

    public int getUpperX() {
        return this.x2;
    }

    public int getUpperY() {
        return this.y2;
    }

    public int getUpperZ() {
        return this.z2;
    }

    public Block[] corners() {
        Block[] res = new Block[8];
        World w = getWorld();
        res[0] = w.getBlockAt(this.x1, this.y1, this.z1);
        res[1] = w.getBlockAt(this.x1, this.y1, this.z2);
        res[2] = w.getBlockAt(this.x1, this.y2, this.z1);
        res[3] = w.getBlockAt(this.x1, this.y2, this.z2);
        res[4] = w.getBlockAt(this.x2, this.y1, this.z1);
        res[5] = w.getBlockAt(this.x2, this.y1, this.z2);
        res[6] = w.getBlockAt(this.x2, this.y2, this.z1);
        res[7] = w.getBlockAt(this.x2, this.y2, this.z2);
        return res;
    }

    public Block[] minCorners() {
        Block[] res = new Block[4];
        World w = getWorld();
        res[0] = w.getBlockAt(this.x1, this.y1, this.z1);
        return res;
    }

    public Cuboid expand(CuboidDirection dir, int amount) {
        switch (dir) {
            case NORTH:
                return new Cuboid(this.worldName, this.x1 - amount, this.y1, this.z1, this.x2, this.y2, this.z2);
            case SOUTH:
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2 + amount, this.y2, this.z2);
            case EAST:
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1 - amount, this.x2, this.y2, this.z2);
            case WEST:
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2 + amount);
            case DOWN:
                return new Cuboid(this.worldName, this.x1, this.y1 - amount, this.z1, this.x2, this.y2, this.z2);
            case UP:
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2 + amount, this.z2);
        }
        throw new IllegalArgumentException("Invalid direction " + dir);
    }

    public Cuboid shift(CuboidDirection dir, int amount) {
        return expand(dir, amount).expand(dir.opposite(), -amount);
    }

    public Cuboid outset(CuboidDirection dir, int amount) {
        Cuboid c;
        switch (dir) {
            case HORIZONTAL:
                c = expand(CuboidDirection.NORTH, amount).expand(CuboidDirection.SOUTH, amount).expand(CuboidDirection.EAST, amount).expand(CuboidDirection.WEST, amount);
                return c;
            case VERTICAL:
                c = expand(CuboidDirection.DOWN, amount).expand(CuboidDirection.UP, amount);
                return c;
            case BOTH:
                c = outset(CuboidDirection.HORIZONTAL, amount).outset(CuboidDirection.VERTICAL, amount);
                return c;
        }
        throw new IllegalArgumentException("Invalid direction " + dir);
    }

    public Cuboid inset(CuboidDirection dir, int amount) {
        return outset(dir, -amount);
    }

    public boolean contains(int x, int y, int z) {
        return (x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2);
    }

    public boolean contains(Block b) {
        return contains(b.getLocation());
    }

    public boolean contains(Location l) {
        if (!this.worldName.equals(l.getWorld().getName()))
            return false;
        return contains(l.getBlockX(), l.getBlockY(), l.getBlockZ());
    }

    public boolean contains(Entity e) {
        return contains(e.getLocation());
    }

    public Cuboid grow(int i) {
        return expand(CuboidDirection.NORTH, i).expand(CuboidDirection.SOUTH, i)
                .expand(CuboidDirection.EAST, i).expand(CuboidDirection.WEST, i);
    }

    public int getVolume() {
        return getSizeX() * getSizeY() * getSizeZ();
    }

    public byte getAverageLightLevel() {
        long total = 0L;
        int n = 0;
        for (Block b : this) {
            if (b.isEmpty()) {
                total += b.getLightLevel();
                n++;
            }
        }
        return (n > 0) ? (byte)(int)(total / n) : 0;
    }

    public Cuboid contract() {
        return contract(CuboidDirection.DOWN).contract(CuboidDirection.SOUTH)
                .contract(CuboidDirection.EAST).contract(CuboidDirection.UP)
                .contract(CuboidDirection.NORTH).contract(CuboidDirection.WEST);
    }

    public Cuboid contract(CuboidDirection dir) {
        Cuboid face = getFace(dir.opposite());
        switch (dir) {
            case DOWN:
                while (face.containsOnly(0) && face.getLowerY() > getLowerY())
                    face = face.shift(CuboidDirection.DOWN, 1);
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, face
                        .getUpperY(), this.z2);
            case UP:
                while (face.containsOnly(0) && face.getUpperY() < getUpperY())
                    face = face.shift(CuboidDirection.UP, 1);
                return new Cuboid(this.worldName, this.x1, face.getLowerY(), this.z1, this.x2, this.y2, this.z2);
            case NORTH:
                while (face.containsOnly(0) && face.getLowerX() > getLowerX())
                    face = face.shift(CuboidDirection.NORTH, 1);
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, face.getUpperX(), this.y2, this.z2);
            case SOUTH:
                while (face.containsOnly(0) && face.getUpperX() < getUpperX())
                    face = face.shift(CuboidDirection.SOUTH, 1);
                return new Cuboid(this.worldName, face.getLowerX(), this.y1, this.z1, this.x2, this.y2, this.z2);
            case EAST:
                while (face.containsOnly(0) && face.getLowerZ() > getLowerZ())
                    face = face.shift(CuboidDirection.EAST, 1);
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, face
                        .getUpperZ());
            case WEST:
                while (face.containsOnly(0) && face.getUpperZ() < getUpperZ())
                    face = face.shift(CuboidDirection.WEST, 1);
                return new Cuboid(this.worldName, this.x1, this.y1, face.getLowerZ(), this.x2, this.y2, this.z2);
        }
        throw new IllegalArgumentException("Invalid direction " + dir);
    }

    public Cuboid getFace(CuboidDirection dir) {
        switch (dir) {
            case DOWN:
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y1, this.z2);
            case UP:
                return new Cuboid(this.worldName, this.x1, this.y2, this.z1, this.x2, this.y2, this.z2);
            case NORTH:
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x1, this.y2, this.z2);
            case SOUTH:
                return new Cuboid(this.worldName, this.x2, this.y1, this.z1, this.x2, this.y2, this.z2);
            case EAST:
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z1);
            case WEST:
                return new Cuboid(this.worldName, this.x1, this.y1, this.z2, this.x2, this.y2, this.z2);
        }
        throw new IllegalArgumentException("Invalid direction " + dir);
    }

    public boolean containsOnly(int blockId) {
        for (Block b : this) {
            if (b.getTypeId() != blockId)
                return false;
        }
        return true;
    }

    public Cuboid getBoundingCuboid(Cuboid other) {
        if (other == null)
            return this;
        int xMin = Math.min(getLowerX(), other.getLowerX());
        int yMin = Math.min(getLowerY(), other.getLowerY());
        int zMin = Math.min(getLowerZ(), other.getLowerZ());
        int xMax = Math.max(getUpperX(), other.getUpperX());
        int yMax = Math.max(getUpperY(), other.getUpperY());
        int zMax = Math.max(getUpperZ(), other.getUpperZ());
        return new Cuboid(this.worldName, xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public Block getRelativeBlock(int x, int y, int z) {
        return getWorld().getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
    }

    public Block getRelativeBlock(World w, int x, int y, int z) {
        return w.getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
    }

    public List<Chunk> getChunks() {
        List<Chunk> res = new ArrayList<>();
        World w = getWorld();
        int x1 = getLowerX() & 0xFFFFFFF0;
        int x2 = getUpperX() & 0xFFFFFFF0;
        int z1 = getLowerZ() & 0xFFFFFFF0;
        int z2 = getUpperZ() & 0xFFFFFFF0;
        for (int x = x1; x <= x2; x += 16) {
            for (int z = z1; z <= z2; z += 16)
                res.add(w.getChunkAt(x >> 4, z >> 4));
        }
        return res;
    }

    public Iterator<Block> iterator() {
        return new CuboidIterator(getWorld(), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
    }

    public Iterator<Location> locationIterator() {
        return new CuboidLocationIterator(this.getWorld(), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
    }

    public List<Vector> edges() {
        return this.edges(-1, -1, -1, -1);
    }

    public Location getMinimumPoint() {
        return new Location(this.getWorld(), (double) Math.min(this.x1, this.x2), (double) Math.min(this.y1, this.y2), (double) Math.min(this.z1, this.z2));
    }

    public Location getMaximumPoint() {
        return new Location(this.getWorld(), (double) Math.max(this.x1, this.x2), (double) Math.max(this.y1, this.y2), (double) Math.max(this.z1, this.z2));
    }

    public List<Vector> edges(int fixedMinX, int fixedMaxX, int fixedMinZ, int fixedMaxZ) {
        Vector v1 = getMinimumPoint().toVector();
        Vector v2 = getMaximumPoint().toVector();
        int minX = v1.getBlockX();
        int maxX = v2.getBlockX();
        int minZ = v1.getBlockZ();
        int maxZ = v2.getBlockZ();
        int capacity = (maxX - minX) * 4 + (maxZ - minZ) * 4;
        ArrayList<Vector> result = new ArrayList<Vector>(capacity += 4);
        if (capacity <= 0) {
            return result;
        }
        int minY = v1.getBlockY();
        int maxY = v1.getBlockY();
        for (int x = minX; x <= maxX; ++x) {
            result.add(new Vector(x, minY, minZ));
            result.add(new Vector(x, minY, maxZ));
            result.add(new Vector(x, maxY, minZ));
            result.add(new Vector(x, maxY, maxZ));
        }
        for (int z = minZ; z <= maxZ; ++z) {
            result.add(new Vector(minX, minY, z));
            result.add(new Vector(minX, maxY, z));
            result.add(new Vector(maxX, minY, z));
            result.add(new Vector(maxX, maxY, z));
        }
        return result;
    }

    public Cuboid clone() {
        return new Cuboid(this);
    }

    public String toString() {
        return new String("Cuboid: " + this.worldName + "," + this.x1 + "," + this.y1 + "," + this.z1 + "=>" + this.x2 + "," + this.y2 + "," + this.z2);
    }

    public List<Block> getWalls() {
        List<Block> blocks = new ArrayList<>();
        Location min = new Location(getWorld(), this.x1, this.y1, this.z1);
        Location max = new Location(getWorld(), this.x2, this.y2, this.z2);
        int minX = min.getBlockX();
        int minY = min.getBlockY();
        int minZ = min.getBlockZ();
        int maxX = max.getBlockX();
        int maxY = max.getBlockY();
        int maxZ = max.getBlockZ();
        for (int x = minX; x <= maxX; x++) {
            for (int i = minY; i <= maxY; i++) {
                Location minLoc = new Location(getWorld(), x, i, minZ);
                Location maxLoc = new Location(getWorld(), x, i, maxZ);
                blocks.add(minLoc.getBlock());
                blocks.add(maxLoc.getBlock());
            }
        }
        for (int y = minY; y <= maxY; y++) {
            for (int z = minZ; z <= maxZ; z++) {
                Location minLoc = new Location(getWorld(), minX, y, z);
                Location maxLoc = new Location(getWorld(), maxX, y, z);
                blocks.add(minLoc.getBlock());
                blocks.add(maxLoc.getBlock());
            }
        }
        return blocks;
    }

    public List<Block> getFaces() {
        List<Block> blocks = new ArrayList<>();
        Location min = new Location(getWorld(), this.x1, this.y1, this.z1);
        Location max = new Location(getWorld(), this.x2, this.y2, this.z2);
        int minX = min.getBlockX();
        int minY = min.getBlockY();
        int minZ = min.getBlockZ();
        int maxX = max.getBlockX();
        int maxY = max.getBlockY();
        int maxZ = max.getBlockZ();
        for (int x = minX; x <= maxX; x++) {
            for (int i = minY; i <= maxY; i++) {
                blocks.add((new Location(getWorld(), x, i, minZ)).getBlock());
                blocks.add((new Location(getWorld(), x, i, maxZ)).getBlock());
            }
        }
        for (int y = minY; y <= maxY; y++) {
            for (int i = minZ; i <= maxZ; i++) {
                blocks.add((new Location(getWorld(), minX, y, i)).getBlock());
                blocks.add((new Location(getWorld(), maxX, y, i)).getBlock());
            }
        }
        for (int z = minZ; z <= maxZ; z++) {
            for (int i = minX; i <= maxX; i++) {
                blocks.add((new Location(getWorld(), i, minY, z)).getBlock());
                blocks.add((new Location(getWorld(), i, maxY, z)).getBlock());
            }
        }
        return blocks;
    }

    public enum CuboidDirection {
        NORTH, EAST, SOUTH, WEST, UP, DOWN, HORIZONTAL, VERTICAL, BOTH, UNKNOWN;

        public CuboidDirection opposite() {
            switch (this) {
                case NORTH:
                    return SOUTH;
                case EAST:
                    return WEST;
                case SOUTH:
                    return NORTH;
                case WEST:
                    return EAST;
                case HORIZONTAL:
                    return VERTICAL;
                case VERTICAL:
                    return HORIZONTAL;
                case UP:
                    return DOWN;
                case DOWN:
                    return UP;
                case BOTH:
                    return BOTH;
            }
            return UNKNOWN;
        }
    }

    public class CuboidIterator implements Iterator<Block> {
        private World w;

        private int baseX;

        private int baseY;

        private int baseZ;

        private int x;

        private int y;

        private int z;

        private int sizeX;

        private int sizeY;

        private int sizeZ;

        public CuboidIterator(World w, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.w = w;
            this.baseX = Math.min(x1, x2);
            this.baseY = Math.min(y1, y2);
            this.baseZ = Math.min(z1, z2);
            this.sizeX = Math.abs(x2 - x1) + 1;
            this.sizeY = Math.abs(y2 - y1) + 1;
            this.sizeZ = Math.abs(z2 - z1) + 1;
            this.x = this.y = this.z = 0;
        }

        public boolean hasNext() {
            return (this.x < this.sizeX && this.y < this.sizeY && this.z < this.sizeZ);
        }

        public Block next() {
            Block b = this.w.getBlockAt(this.baseX + this.x, this.baseY + this.y, this.baseZ + this.z);
            this.x = 0;
            if (++this.x >= this.sizeX && ++this.y >= this.sizeY) {
                this.y = 0;
                this.z++;
            }
            return b;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public class CuboidLocationIterator implements Iterator<Location> {

        private final World world;
        private final int baseX;
        private final int baseY;
        private final int baseZ;
        private final int sizeX;
        private final int sizeY;
        private final int sizeZ;
        private int x;
        private int y;
        private int z;

        public CuboidLocationIterator(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.world = world;
            this.baseX = x1;
            this.baseY = y1;
            this.baseZ = z1;
            this.sizeX = Math.abs(x2 - x1) + 1;
            this.sizeY = Math.abs(y2 - y1) + 1;
            this.sizeZ = Math.abs(z2 - z1) + 1;
            this.z = 0;
            this.y = 0;
            this.x = 0;
        }

        @Override
        public boolean hasNext() {
            return this.x < this.sizeX && this.y < this.sizeY && this.z < this.sizeZ;
        }

        @Override
        public Location next() {
            Location location = new Location(this.world, (double) (this.baseX + this.x), (double) (this.baseY + this.y), (double) (this.baseZ + this.z));
            if (++this.x >= this.sizeX) {
                this.x = 0;
                if (++this.y >= this.sizeY) {
                    this.y = 0;
                    ++this.z;
                }
            }
            return location;
        }

        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

}
