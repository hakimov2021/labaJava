import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityIndex;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;

public class Store {
    private static File envStore = new File("./JEDB");
    private Environment envmnt;
    private EntityStore store;
    private DataAccessor da;

    public void setup() {
        EnvironmentConfig envConfig = new EnvironmentConfig();
        StoreConfig storeConfig = new StoreConfig();

        envConfig.setAllowCreate(true);
        storeConfig.setAllowCreate(true);
        envmnt = new Environment(envStore, envConfig);
        store = new EntityStore(envmnt, "EntityStore", storeConfig);
    }

    public void shutdown() throws DatabaseException {
        store.close();
        envmnt.close();
    }

    public void run() throws DatabaseException {

        setup();

        da = new DataAccessor(store);

        Department department = new Department(1, "New  Dep");

        Emploee emploee1 = new Emploee(1, department.getId(), "name 1");

        da.dIdx.put(department);

        da.eIdx.put(emploee1);

        Emploee id = getEmploee(1);

        shutdown();
    }

    /**
     * Получение одной записи по первичному ключу
     *
     * @param id
     * @return
     */
    public Emploee getEmploee(int id) {
        return da.eIdx.get(id);
    }

    /**
     * Удаление одной записи
     *
     * @param id
     * @return
     */
    public boolean deleteEmploee(int id) {
        return da.eIdx.delete(id);
    }

    /**
     * Получение списка всех зависимых записей
     *
     * @param id
     * @return
     */
    public Emploee getRelationOrder(int id) {
        Emploee emploee = da.eIdx.get(id);
        int departmentId = emploee.getDepId();
        Department department = da.dIdx.get(departmentId);
        emploee.setDepartment(department);

        return emploee;
    }

    /**
     * Получение списка всех записей
     *
     * @return
     */
    public HashMap getEmploees() {
        HashMap<Integer, Emploee> orders = new HashMap<Integer, Emploee>();

        for (Map.Entry<Integer, Emploee> entry : da.eIdx.map().entrySet()) {
            orders.put(entry.getValue().getId(), entry.getValue());
        }

        return orders;
    }

    /**
     * Поиск записей по первичному ключу
     *
     * @param searchId
     * @return
     */
    public Emploee getEmploeeById(int searchId) {
        setup();

        EntityIndex<Integer, Emploee> pIndex = da.eIdx;

        EntityCursor<Emploee> org_cursor = pIndex.entities();

        for (Emploee entity : org_cursor) {

            int id = org_cursor.current().getId();
            if (searchId == id) {
                return entity;
            }
        }

        return new Emploee(1, 1, "false");
    }

    /**
     * Обновление найденной по первичному ключу записи
     *
     * @param searchId
     * @param emploee
     * @return
     */
    public Emploee updateOrderById(int searchId, Emploee emploee) {
        setup();

        EntityIndex<Integer, Emploee> pIndex = da.eIdx;

        EntityCursor<Emploee> org_cursor = pIndex.entities();

        for (Emploee entity : org_cursor) {

            int id = org_cursor.current().getId();
            if (searchId == id) {
                org_cursor.update(emploee);
            }
        }

        return new Emploee(1, 1, "false");
    }

}
