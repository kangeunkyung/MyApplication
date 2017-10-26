package atsoultions.eunkong.myapplication.database;

/**
 * Created by eunkong on 2017. 10. 19..
 */

public class ContactDB {

    private ContactDB() {
    }

    public static final String TBL_CONTACT      = "CONTACT";
    public static final String _ID              = "NO";
    public static final String COL_BANK         = "BANK";
    public static final String COL_BANK_INDEX   = "BANK_INDEX";
    public static final String COL_USER         = "USER";
    public static final String COL_ACCOUNT      = "ACCOUNT";
    public static final String COL_NICKNAME     = "NICKNAME";


    public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_CONTACT +
            "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ", " +
            COL_BANK + " TEXT NOT NULL" + ", " +
            COL_BANK_INDEX + " INTEGER NOT NULL" + ", " +
            COL_ACCOUNT + " TEXT NOT NULL" + ", " +
            COL_USER + " TEXT" + ", " +
            COL_NICKNAME + " TEXT" +
            ")";


    // DROP TABLE IF EXISTS CONTACT
    public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_CONTACT;

    // SELECT * FROM CONTACT
    public static final String SQL_SELECT = "SELECT * FROM " + TBL_CONTACT;

    public static final String SQL_SELECT_FROM_NO = "SELECT * FROM " + TBL_CONTACT + " WHERE " + _ID + "=";

    // INSERT OR REPLACE INTO CONTACT (NO, NAME, PHONE) VALUES (X,X,X)
    public static final String SQL_INSERT_DEFAULT = "INSERT INTO " + TBL_CONTACT + " " +
            "(" + COL_BANK + ", " + COL_BANK_INDEX + ", " + COL_ACCOUNT + ") VALUES ";

    public static final String SQL_INSERT_OPTIONAL = "INSERT INTO " + TBL_CONTACT + " " +
            "(" + COL_BANK + ", " + COL_BANK_INDEX + ", " + COL_ACCOUNT + ", " + COL_USER + ", " + COL_NICKNAME + ") VALUES ";

    public static final String SQL_INSERT_ALL = "INSERT INTO CONTACT (NO, BANK, BANK_INDEX, ACCOUNT, USER, NICKNAME) VALUES (?,?,?,?,?)";

    // DELETE FROM CONTACT
    public static final String SQL_DELETE_ALL = "DELETE FROM " + TBL_CONTACT;


    // DELETE FROM CONTACT
    public static final String SQL_DELETE_ONE = "DELETE FROM " + TBL_CONTACT + " WHERE " + _ID + "=";


}
