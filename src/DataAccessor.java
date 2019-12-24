import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;

public class DataAccessor {
    public DataAccessor(EntityStore store) throws DatabaseException {

        eIdx = store.getPrimaryIndex(
                Integer.class, Emploee.class);

        dIdx = store.getPrimaryIndex(
                Integer.class, Department.class);

        sIdx = store.getSecondaryIndex(
                eIdx, Integer.class, "dep_id");

    }

    PrimaryIndex<Integer, Emploee> eIdx;
    PrimaryIndex<Integer, Department> dIdx;
    SecondaryIndex<Integer, Integer, Emploee> sIdx;
}