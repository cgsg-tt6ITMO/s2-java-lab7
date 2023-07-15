/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.task;

import java.time.ZonedDateTime;

import static java.lang.Math.pow;
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
        setDistance();
    }

    /**
     * Route constructor.
     */
    public Route(Long id, String Name, Coordinates coords, Location f, Location t) {
        this.setId(id)
            .setName(Name)
            .setCoordinates(coords)
            .setFrom(f)
            .setTo(t)
            .setDistance();
    }

    public Route setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Route setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Route setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public Route setTo(Location to) {
        this.to = to;
        return this;
    }

    public Location getTo() {
        return to;
    }

    public Route setFrom(Location from) {
        this.from = from;
        return this;
    }

    public Location getFrom() {
        return from;
    }

    public Route setDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    public Route setDistance() {
        Double dist = sqrt(pow(getFrom().getX() - getTo().getX(), 2)
                    + pow(getFrom().getY() - getTo().getY(), 2)
                    + pow(getFrom().getZ() - getTo().getZ(), 2));
        return setDistance(dist);
    }

    public Double getDistance() {
        return distance;
    }
}
