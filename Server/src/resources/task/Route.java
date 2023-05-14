/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.task;

import java.time.ZonedDateTime;

import static java.lang.Math.sqrt;

/**
 * Element of the collection.
 */
public class Route implements Comparable<Route> {
    private Long id;                    // not null, > 0, unique, auto gen
    private String name;                // not null, not ""
    private Coordinates coordinates;    // not null
    private ZonedDateTime creationTime; // auto gen
    private Location from;              // not null
    private Location to;
    private Double distance;            // > 1

    @Override
    public String toString() {
        return "Name:" + name;
    }

    @Override
    public int hashCode() {
        return (int)(Math.round(Double.parseDouble(String.valueOf(distance*1000))));
    }

    /**
     * Compares this route by distance with the one in argument.
     * @param r - Route to be compared with
     * @return <ul><li>1 - this > r</li>
     *             <li>1 - this &lt; r</li>
     *             <li>0 - this = r</li></ul>
     */
    @Override
    public int compareTo(Route r) {
        return Double.compare(distance, r.getDistance());
    }

    /**
     * Default constructor for debugging.
     */
    public Route() {
        setId(1L);
        setCreationTime(ZonedDateTime.now());
        setName("route#" + id);
        setCoordinates(new Coordinates(5.17, 3.41f));
        setFrom(new Location());
        setTo(new Location(1.34f, 5.61f, 45, "loc-to"));
        Double dist = sqrt((getFrom().getX()-getTo().getX()) * (getFrom().getX()-getTo().getX())
                + (getFrom().getY()-getTo().getY()) * (getFrom().getY()-getTo().getY())
                + (getFrom().getZ()-getTo().getZ()) * (getFrom().getZ()-getTo().getZ()));
        setDistance(dist);
    }

    /**
     * Route constructor.
     */
    public Route(Long id, String Name, Coordinates coords, Location f, Location t) {
        setId(id);
        setName(Name);
        setCoordinates(coords);
        setFrom(f);
        setTo(t);
        Double dist = sqrt((f.getX()-t.getX()) * (f.getX()-t.getX())
                + (f.getY()-t.getY()) * (f.getY()-t.getY())
                + (f.getZ()-t.getZ()) * (f.getZ()-t.getZ()));
        setDistance(dist);
    }

    public Route setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * @return id of the Route.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets name and suggests you to correct your output.
     */
    public Route setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return Name of the Route.
     */
    public String getName() {
        return name;
    }

    /**
     * @param coordinates set coordinates for route.
     */
    public Route setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    /**
     * @return Coordinates of this Route.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @param creationTime time when the collection was created.
     */
    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @return time when the collection was created.
     */
    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    /**
     * Sets where do we go to.
     * @param to != null, Location - point of destination.
     */
    public Route setTo(Location to) {
        this.to = to;
        return this;
    }

    /**
     * @return Location To of the Route.
     */
    public Location getTo() {
        return to;
    }

    /**
     * Sets where do we come from.
     * @param from != null, Location - the beginning of our route.
     */
    public Route setFrom(Location from) {
        this.from = from;
        return this;
    }

    public Location getFrom() {
        return from;
    }

    /**
     * Sets the length of the route.
     * @param distance - the length (long)
     */
    public Route setDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    public Route setDistance() {
        Double dist = sqrt((getFrom().getX() - getTo().getX()) * (getFrom().getX() - getTo().getX())
                    + (getFrom().getY() - getTo().getY()) * (getFrom().getY() - getTo().getY())
                    + (getFrom().getZ() - getTo().getZ()) * (getFrom().getZ() - getTo().getZ()));
        return setDistance(dist);
    }

    public Double getDistance() {
        return distance;
    }
}
