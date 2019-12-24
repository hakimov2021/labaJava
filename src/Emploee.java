import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

@Entity
public class Emploee {
    @PrimaryKey(sequence = "ID")
    private int id;
    @SecondaryKey(relate = Relationship.MANY_TO_ONE, relatedEntity = Department.class, name = "dep_id")
    private int dep_id;

    private Department department;
    private String name;

    private Emploee() {
    }

    public Emploee(int id, int dep_id, String name) {
        this.id = id;
        this.dep_id = dep_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getDepId() {
        return dep_id;
    }

    public String getName() {
        return name;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }
}
