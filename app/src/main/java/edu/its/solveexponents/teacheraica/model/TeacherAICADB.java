package edu.its.solveexponents.teacheraica.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jairus on 8/7/16.
 */

public class TeacherAICADB extends SQLiteOpenHelper {
    private static TeacherAICADB sInstance;

    private SQLiteDatabase db;
    private int problemID;
    private DateFormat dateFormat;
    private Date dateCreated;
    private Date dateStopped;
    private int stepID;
    private int hintNumber;
    private int errorNumber;

    private Context context;

    private int maxNumberOfProblemsPerSublevel;

    private static final String DB_NAME = "teacheraicadb";

    private static final int DB_VERSION = 1;

    private final String TBL_PROBLEM_TYPES = "tbl_problem_types";
    private final String TBL_PROBLEM_TYPES_PROBLEMTYPE = "problemType";

    private final String TBL_PROBLEMS = "tbl_problems";
    private final String TBL_PROBLEMS_PROBLEMID = "problemID";
    private final String TBL_PROBLEMS_PROBLEMTYPE = "problemType";
    private final String TBL_PROBLEMS_PROBLEM = "problems";
    private final String TBL_PROBLEMS_STATUS = "status";
    private final String TBL_PROBLEMS_TIMEELAPSED = "timeElapsed";
    private final String TBL_PROBLEMS_DATECREATED = "dateCreated";
    private final String TBL_PROBLEMS_DATESTOPPED = "dateStopped";

    private final String TBL_STEPS = "tbl_steps";
    private final String TBL_STEPS_PROBLEMID = "problemID";
    private final String TBL_STEPS_STEPID = "stepID";
    private final String TBL_STEPS_STEP = "step";
    private final String TBL_STEPS_TIMESTAMP = "timeStamp";

    private final String TBL_HINT_TYPES = "tbl_hint_types";
    private final String TBL_HINT_TYPES_HINTCODE = "hintCode";
    private final String TBL_HINT_TYPES_HINTDEFINITION = "hintDefinition";

    private final String TBL_HINTS_USED = "tbl_hints_used";
    private final String TBL_HINTS_USED_PROBLEMID = "problemID";
    private final String TBL_HINTS_USED_STEPID = "stepID";
    private final String TBL_HINTS_USED_HINTNUMBER = "hintNumber";
    private final String TBL_HINTS_USED_HINTCODE = "hintCode";
    private final String TBL_HINTS_USED_HINT = "hint";
    private final String TBL_HINTS_USED_TIMESTAMP = "timeStamp";

    private final String TBL_ERRORS = "tbl_errors";
    private final String TBL_ERRORS_PROBLEMID = "problemID";
    private final String TBL_ERRORS_STEPID = "stepID";
    private final String TBL_ERRORS_ERRORNUMBER = "errorNumber";
    private final String TBL_ERRORS_ERROR = "error";
    private final String TBL_ERRORS_TIMESTAMP = "timeStamp";

    private final String TBL_LEVELS = "tbl_levels";
    private final String TBL_LEVELS_1_1 = "level1_1";
    private final String TBL_LEVELS_1_2 = "level1_2";
    private final String TBL_LEVELS_1_3 = "level1_3";
    private final String TBL_LEVELS_1_4 = "level1_4";
    private final String TBL_LEVELS_2_1 = "level2_1";
    private final String TBL_LEVELS_2_2 = "level2_2";
    private final String TBL_LEVELS_3_1 = "level3_1";
    private final String TBL_LEVELS_3_2 = "level3_2";
    private final String TBL_LEVELS_3_3 = "level3_3";
    private final String TBL_LEVELS_4_1 = "level4_1";
    private final String TBL_LEVELS_4_2 = "level4_2";
    private final String TBL_LEVELS_4_3 = "level4_3";

    private final String TBL_LECTURES_MODULE_LOGS = "tbl_lectures_module_logs";
    private final String TBL_LECTURES_MODULE_LOGS_PAGE = "page";
    private final String TBL_LECTURES_MODULE_LOGS_IN = "inTimestamp";
    private final String TBL_LECTURES_MODULE_LOGS_OUT = "outTimestamp";
    private final String TBL_LECTURES_MODULE_LOGS_ELAPSED = "timeElapsed";

    private final String TBL_SYSTEM_ERRORS = "tbl_system_errors";
    private final String TBL_SYSTEM_ERRORS_PROBLEM_OR_MESSAGE = "problemOrMessage";
    private final String TBL_SYSTEM_ERRORS_TIMESTAMP = "timeStamp";

    public static synchronized TeacherAICADB getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx

        if (sInstance == null) {
            sInstance = new TeacherAICADB(context.getApplicationContext());
        }

        return sInstance;
    }

    public TeacherAICADB(Context context) {
        super(context, Environment.getExternalStorageDirectory().getAbsolutePath() + "/teacheraica/" + DB_NAME, null, DB_VERSION);

        Log.d("TEACHERAICADB", Environment.getExternalStorageDirectory().getPath());

        maxNumberOfProblemsPerSublevel = 4;
        this.context = context;
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String create_tbl_problem_types = "CREATE TABLE IF NOT EXISTS " + TBL_PROBLEM_TYPES +
                " (" +
                TBL_PROBLEM_TYPES_PROBLEMTYPE + " VARCHAR PRIMARY KEY" +
                ");";
        try{
            db.execSQL(create_tbl_problem_types);
        } catch (SQLException e) {
            Log.e("TEACHERAICADB", ""+e);
        }

        String create_tbl_problems = "CREATE TABLE IF NOT EXISTS " + TBL_PROBLEMS +
                " (" +
                TBL_PROBLEMS_PROBLEMID + " INTEGER PRIMARY KEY, " +
                TBL_PROBLEMS_PROBLEMTYPE + " VARCHAR, " +
                TBL_PROBLEMS_PROBLEM + " VARCHAR, " +
                TBL_PROBLEMS_STATUS + " VARCHAR, " +
                TBL_PROBLEMS_TIMEELAPSED + " INTEGER, " +
                TBL_PROBLEMS_DATECREATED + " DATE, " +
                TBL_PROBLEMS_DATESTOPPED + " DATE, " +
                "FOREIGN KEY (" + TBL_PROBLEMS_PROBLEMTYPE + ") REFERENCES " + TBL_PROBLEM_TYPES + " (" + TBL_PROBLEM_TYPES_PROBLEMTYPE + ")" +
                ");";
        try{
            db.execSQL(create_tbl_problems);
        } catch (SQLException e) {
            Log.e("TEACHERAICADB", ""+e);
        }

        String create_tbl_steps = "CREATE TABLE IF NOT EXISTS " + TBL_STEPS +
                " (" +
                TBL_STEPS_PROBLEMID + " INTEGER, " +
                TBL_STEPS_STEPID + " INTEGER, " +
                TBL_STEPS_STEP + " VARCHAR, " +
                TBL_STEPS_TIMESTAMP + " DATE, " +
                "PRIMARY KEY(" + TBL_STEPS_PROBLEMID + ", " + TBL_STEPS_STEPID + "), " +
                "FOREIGN KEY (" + TBL_STEPS_PROBLEMID + ") REFERENCES " + TBL_PROBLEMS + " (" + TBL_PROBLEMS_PROBLEMID + ")" +
                ");";

        try{
            db.execSQL(create_tbl_steps);
        } catch (SQLException e) {
            Log.e("TEACHERAICADB", ""+e);
        }

        String create_tbl_hint_types = "CREATE TABLE IF NOT EXISTS " + TBL_HINT_TYPES +
                " (" +
                TBL_HINT_TYPES_HINTCODE + " VARCHAR PRIMARY KEY, " +
                TBL_HINT_TYPES_HINTDEFINITION + " VARCHAR" +
                ");";

        try{
            db.execSQL(create_tbl_hint_types);
        } catch (SQLException e) {
            Log.e("TEACHERAICADB", ""+e);
        }

        String create_tbl_hints_used = "CREATE TABLE IF NOT EXISTS " + TBL_HINTS_USED +
                " (" +
                TBL_HINTS_USED_PROBLEMID + " INTEGER, " +
                TBL_HINTS_USED_STEPID + " INTEGER, " +
                TBL_HINTS_USED_HINTNUMBER + " INTEGER, " +
                TBL_HINTS_USED_HINTCODE + " VARCHAR, " +
                TBL_HINTS_USED_HINT + " VARCHAR, " +
                TBL_HINTS_USED_TIMESTAMP + " DATE, " +
                "PRIMARY KEY(" + TBL_HINTS_USED_PROBLEMID + ", " + TBL_HINTS_USED_STEPID + ", " + TBL_HINTS_USED_HINTNUMBER + "), " +
                "FOREIGN KEY (" + TBL_HINTS_USED_PROBLEMID + ") REFERENCES " + TBL_PROBLEMS + " (" + TBL_PROBLEMS_PROBLEMID + "), " +
                "FOREIGN KEY (" + TBL_HINTS_USED_STEPID + ") REFERENCES " + TBL_STEPS + " (" + TBL_STEPS_STEPID + ")" +
                ");";

        try{
            db.execSQL(create_tbl_hints_used);
        } catch (SQLException e) {
            Log.e("TEACHERAICADB", ""+e);
        }

        String create_tbl_errors = "CREATE TABLE IF NOT EXISTS " + TBL_ERRORS +
                " (" +
                TBL_ERRORS_PROBLEMID + " INTEGER, " +
                TBL_ERRORS_STEPID + " INTEGER, " +
                TBL_ERRORS_ERRORNUMBER + " INTEGER, " +
                TBL_ERRORS_ERROR + " VARCHAR, " +
                TBL_ERRORS_TIMESTAMP + " DATE, " +
                "PRIMARY KEY(" + TBL_ERRORS_PROBLEMID + ", " + TBL_ERRORS_STEPID + ", " + TBL_ERRORS_ERRORNUMBER + "), " +
                "FOREIGN KEY (" + TBL_ERRORS_PROBLEMID + ") REFERENCES " + TBL_PROBLEMS + " (" + TBL_PROBLEMS_PROBLEMID + "), " +
                "FOREIGN KEY (" + TBL_ERRORS_STEPID + ") REFERENCES " + TBL_STEPS + " (" + TBL_STEPS_STEPID + ")" +
                ");";

        try{
            db.execSQL(create_tbl_errors);
        } catch (SQLException e) {
            Log.e("TEACHERAICADB", ""+e);
        }

        String create_tbl_levels = "CREATE TABLE IF NOT EXISTS " + TBL_LEVELS +
                " (" +
                TBL_LEVELS_1_1 + " INTEGER, " +
                TBL_LEVELS_1_2 + " INTEGER, " +
                TBL_LEVELS_1_3 + " INTEGER, " +
                TBL_LEVELS_1_4 + " INTEGER, " +
                TBL_LEVELS_2_1 + " INTEGER, " +
                TBL_LEVELS_2_2 + " INTEGER, " +
                TBL_LEVELS_3_1 + " INTEGER, " +
                TBL_LEVELS_3_2 + " INTEGER, " +
                TBL_LEVELS_3_3 + " INTEGER, " +
                TBL_LEVELS_4_1 + " INTEGER, " +
                TBL_LEVELS_4_2 + " INTEGER, " +
                TBL_LEVELS_4_3 + " INTEGER" +
                ");";

        try{
            db.execSQL(create_tbl_levels);
        } catch (SQLException e) {
            Log.e("TEACHERAICADB", ""+e);
        }

        String create_tbl_lectures_module_logs = "CREATE TABLE IF NOT EXISTS " + TBL_LECTURES_MODULE_LOGS +
                " (" +
                TBL_LECTURES_MODULE_LOGS_PAGE + " VARCHAR, " +
                TBL_LECTURES_MODULE_LOGS_IN + " DATE, " +
                TBL_LECTURES_MODULE_LOGS_OUT + " DATE, " +
                TBL_LECTURES_MODULE_LOGS_ELAPSED + " INT" +
                ");";

        try{
            db.execSQL(create_tbl_lectures_module_logs);
        } catch (SQLException e) {
            Log.e("TEACHERAICADB", ""+e);
        }

        String create_tbl_system_errors = "CREATE TABLE IF NOT EXISTS " + TBL_SYSTEM_ERRORS +
                " (" +
                TBL_SYSTEM_ERRORS_PROBLEM_OR_MESSAGE + " VARCHAR, " +
                TBL_STEPS_TIMESTAMP + " DATE" +
                ");";

        try{
            db.execSQL(create_tbl_system_errors);
        } catch (SQLException e) {
            Log.e("TEACHERAICADB", ""+e);
        }

        if (isTableEmpty(db, TBL_LEVELS)) {
            String initializeLevelsValues = "INSERT INTO " + TBL_LEVELS +
                    " VALUES (0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);";
            db.execSQL(initializeLevelsValues);
        }

        ContentValues values = new ContentValues();

        if (isTableEmpty(db, TBL_PROBLEM_TYPES)) {
            values.put(TBL_PROBLEMS_PROBLEMTYPE, "custom");
            db.insert(TBL_PROBLEM_TYPES, null, values);
            values.put(TBL_PROBLEMS_PROBLEMTYPE, "generated");
            db.insert(TBL_PROBLEM_TYPES, null, values);
        }

        if (isTableEmpty(db, TBL_HINT_TYPES)) {
            values = new ContentValues();
            values.put(TBL_HINT_TYPES_HINTCODE, "Z");
            values.put(TBL_HINT_TYPES_HINTDEFINITION, "Base Raised to Zero");
            db.insert(TBL_HINT_TYPES, null, values);
            values.put(TBL_HINT_TYPES_HINTCODE, "AE");
            values.put(TBL_HINT_TYPES_HINTDEFINITION, "Addition of Exponents with the Same Bases");
            db.insert(TBL_HINT_TYPES, null, values);
            values.put(TBL_HINT_TYPES_HINTCODE, "MB");
            values.put(TBL_HINT_TYPES_HINTDEFINITION, "Multiplication of Bases with the Same Exponents");
            db.insert(TBL_HINT_TYPES, null, values);
            values.put(TBL_HINT_TYPES_HINTCODE, "ME");
            values.put(TBL_HINT_TYPES_HINTDEFINITION, "Multiplication of Exponents to Find the Power of a Power");
            db.insert(TBL_HINT_TYPES, null, values);
            values.put(TBL_HINT_TYPES_HINTCODE, "DB");
            values.put(TBL_HINT_TYPES_HINTDEFINITION, "Division of Bases with the Same Exponents");
            values.put(TBL_HINT_TYPES_HINTCODE, "SE");
            values.put(TBL_HINT_TYPES_HINTDEFINITION, "Subtraction of Exponents with the Same Bases");
            db.insert(TBL_HINT_TYPES, null, values);
            values.put(TBL_HINT_TYPES_HINTCODE, "N");
            values.put(TBL_HINT_TYPES_HINTDEFINITION, "Negative Integer Exponents");
            db.insert(TBL_HINT_TYPES, null, values);
        }
    }

    private boolean isTableEmpty(SQLiteDatabase db, String table) {
        String sql = "SELECT * FROM " + table;
        Cursor cursor = db.rawQuery(sql, null);

        return (cursor.getCount() == 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    private void initializeDateTimeFormat() {
        this.dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        this.dateFormat.setTimeZone(TimeZone.getDefault());
    }

    public void addProblem(String problem, String type) {
        initializeDateTimeFormat();
        int problemID = getProblemID() + 1;
        this.problemID = problemID;
        this.dateCreated = new Date();
        this.stepID = 1;
        this.hintNumber = 1;
        this.errorNumber = 1;

        ContentValues values = new ContentValues();
        values.put(TBL_PROBLEMS_PROBLEMID, this.problemID);
        values.put(TBL_PROBLEMS_PROBLEM, problem);
        values.put(TBL_PROBLEMS_PROBLEMTYPE, type);
        values.put(TBL_PROBLEMS_DATECREATED, this.dateFormat.format(this.dateCreated));
        db.insert(TBL_PROBLEMS, null, values);
    }

    private int getProblemID() {
        String sql = "SELECT * FROM " + TBL_PROBLEMS;
        Cursor cursor = db.rawQuery(sql, null);
        int problemID = cursor.getCount();

        return problemID;
    }

    public void updateProblemStatus(String status) {
        initializeDateTimeFormat();
        this.dateStopped = new Date();

        ContentValues values = new ContentValues();
        values.put(TBL_PROBLEMS_STATUS, status);
        values.put(TBL_PROBLEMS_TIMEELAPSED, getTimeElapsed(this.dateStopped, this.dateCreated));
        values.put(TBL_PROBLEMS_DATESTOPPED, this.dateFormat.format(this.dateStopped));

        db.update(TBL_PROBLEMS, values, TBL_PROBLEMS_PROBLEMID + " = ?", new String[] {String.valueOf(this.problemID)});
    }

    private int getTimeElapsed(Date end, Date start) {
        long timeElapsed = end.getTime() - start.getTime();
        return (int) Math.ceil(timeElapsed / 1000);
    }

    public void addStep(String step) {
        initializeDateTimeFormat();

        ContentValues values = new ContentValues();
        values.put(TBL_STEPS_PROBLEMID, this.problemID);
        values.put(TBL_STEPS_STEPID, this.stepID++);
        values.put(TBL_STEPS_STEP, step);
        values.put(TBL_STEPS_TIMESTAMP, this.dateFormat.format(new Date()));

        db.insert(TBL_STEPS, null, values);

        this.hintNumber = 1;
        this.errorNumber = 1;
    }

    public void addHintUsed(String problem, String hintCode, String hint) {
        initializeDateTimeFormat();

        ContentValues values = new ContentValues();
        values.put(TBL_HINTS_USED_PROBLEMID, this.problemID);
        values.put(TBL_HINTS_USED_STEPID, this.stepID);
        values.put(TBL_HINTS_USED_HINTNUMBER, this.hintNumber++);
        values.put(TBL_HINTS_USED_HINTCODE, hintCode);
        values.put(TBL_HINTS_USED_HINT, hint);
        values.put(TBL_HINTS_USED_TIMESTAMP, this.dateFormat.format(new Date()));

        db.insert(TBL_HINTS_USED, null, values);
    }

    public void addError(String problem, String error) {
        initializeDateTimeFormat();

        ContentValues values = new ContentValues();
        values.put(TBL_ERRORS_PROBLEMID, this.problemID);
        values.put(TBL_ERRORS_STEPID, this.stepID);
        values.put(TBL_ERRORS_ERRORNUMBER, this.errorNumber++);
        values.put(TBL_ERRORS_ERROR, error);
        values.put(TBL_ERRORS_TIMESTAMP, this.dateFormat.format(new Date()));

        db.insert(TBL_ERRORS, null, values);
    }

    public ArrayList<Problem> getProblems() {
        String sql = "SELECT " + TBL_PROBLEMS_PROBLEMID + ", " +TBL_PROBLEMS_PROBLEM + ", " + TBL_PROBLEMS_DATECREATED
                + " FROM " + TBL_PROBLEMS
                + " WHERE " + TBL_PROBLEMS_STATUS + " = 'solved'"
                + " ORDER BY " + TBL_PROBLEMS_PROBLEMID + " DESC";
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<Problem> problems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Problem problem = new Problem();
                problem.setproblemID(cursor.getInt(0));
                problem.setProblem(cursor.getString(1));
                String date = (cursor.getString(2) == null) ? "" : cursor.getString(2);
                if (!date.equals("")) {
                    date = date.substring(0, date.indexOf(" "));
                }
                problem.setDate(date);

                {
                    ArrayList<String> solution = new ArrayList<>();
                    String solutionSql = "SELECT " + TBL_STEPS_STEP + " FROM " + TBL_STEPS
                            + " WHERE " + TBL_STEPS_PROBLEMID + " = " + cursor.getInt(0)
                            + " ORDER BY " + TBL_STEPS_STEPID;
                    Cursor solutionCursor = db.rawQuery(solutionSql, null);

                    if (solutionCursor.moveToFirst()) {
                        do {
                            solution.add(solutionCursor.getString(0));
                        } while (solutionCursor.moveToNext());
                    }
                    problem.setSolution(solution);
                }

                problems.add(problem);
            } while (cursor.moveToNext());
        }

        return problems;
    }

    public ArrayList<String> getProblemSolution(int problemId) {
        ArrayList<String> solution = new ArrayList<>();

        String sql = "SELECT " + TBL_STEPS_STEP + " FROM " + TBL_STEPS
                + " WHERE " + TBL_STEPS_PROBLEMID + " = " + problemId
                + " ORDER BY " + TBL_STEPS_STEPID;
        Cursor cursor = db.rawQuery(sql, null);
        Log.d("TEACHERAICADB", sql);

        if (cursor.moveToFirst()) {
            do {
                solution.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return solution;
    }

    public int getCurrentLevel() {
        String[] levels = {TBL_LEVELS_1_1, TBL_LEVELS_1_2, TBL_LEVELS_1_3, TBL_LEVELS_1_4,
                TBL_LEVELS_2_1, TBL_LEVELS_2_2,
                TBL_LEVELS_3_1, TBL_LEVELS_3_2, TBL_LEVELS_3_3,
                TBL_LEVELS_4_1, TBL_LEVELS_4_2, TBL_LEVELS_4_3};

        int currentLevel = 4;

        for (String level : levels) {
            String sql = "SELECT " + level + " FROM " + TBL_LEVELS;
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            int value = cursor.getInt(0);

            if (value < maxNumberOfProblemsPerSublevel) {
                currentLevel = Integer.parseInt(level.substring(5, 6)); //get current level from string
                break;
            }
        }
        return currentLevel;
    }

    public int getCurrentSublevel(int level) {
        ArrayList<String> sublevels = new ArrayList<>();

        switch(level) {
            case 1:
                sublevels.add(TBL_LEVELS_1_1);
                sublevels.add(TBL_LEVELS_1_2);
                sublevels.add(TBL_LEVELS_1_3);
                sublevels.add(TBL_LEVELS_1_4);
                break;
            case 2:
                sublevels.add(TBL_LEVELS_2_1);
                sublevels.add(TBL_LEVELS_2_2);
                break;
            case 3:
                sublevels.add(TBL_LEVELS_3_1);
                sublevels.add(TBL_LEVELS_3_2);
                sublevels.add(TBL_LEVELS_3_3);
                break;
            case 4:
                sublevels.add(TBL_LEVELS_4_1);
                sublevels.add(TBL_LEVELS_4_2);
                sublevels.add(TBL_LEVELS_4_3);
                break;
        }

        int currentSublevel = 1;

        for (int i = 0; i < sublevels.size(); ++i) {
            String sql = "SELECT " + sublevels.get(i) + " FROM " + TBL_LEVELS;
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            int value = cursor.getInt(0);

            Log.d("TEACHERAICADB", "sublevel value:" + value);

            if (value < maxNumberOfProblemsPerSublevel) {
                currentSublevel = Integer.parseInt(sublevels.get(i).substring(7));
                break;
            }

            if ((i + 1) == sublevels.size()) {
                currentSublevel = i + 1;
                break;
            }
        }
        return currentSublevel;
    }

    public void incrementLevelSublevelProblemsCount(int level, int sublevel, int increment) {
        String levelAndSublevel = "level" + level + "_" + sublevel;
        String sql = "UPDATE " + TBL_LEVELS +
                " SET " + levelAndSublevel + " = (" + levelAndSublevel + " + " + increment + ")";
        db.execSQL(sql);
    }

    public int determineHintType() {
        String sql = "SELECT " + TBL_PROBLEMS_PROBLEMID + " FROM " + TBL_PROBLEMS +
                " ORDER BY " + TBL_PROBLEMS_PROBLEMID + " DESC";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() >= 12) {
            int errorCount = 0;
            cursor.moveToFirst();
            for (int i = 0; i <= 5; ++i) {
                String sqlGetErrorCount = "SELECT * FROM " + TBL_ERRORS +
                        " WHERE " + TBL_ERRORS_PROBLEMID + " = " + cursor.getInt(0);
                Cursor errorCursor = db.rawQuery(sqlGetErrorCount, null);

                errorCount += errorCursor.getCount();
                cursor.moveToNext();
            }
            if (errorCount <= 8) {
                return 0;
            }
        }
        return 1;
    }

    public void logLectureModuleView(String module, Date out, Date in) {
        initializeDateTimeFormat();

        ContentValues values = new ContentValues();
        values.put(TBL_LECTURES_MODULE_LOGS_PAGE, module);
        values.put(TBL_LECTURES_MODULE_LOGS_IN, this.dateFormat.format(in));
        values.put(TBL_LECTURES_MODULE_LOGS_OUT, this.dateFormat.format(out));
        values.put(TBL_LECTURES_MODULE_LOGS_ELAPSED, getTimeElapsed(out, in));

        db.insert(TBL_LECTURES_MODULE_LOGS, null, values);
    }

    public void logSystemError(String problemOrMessage) {
        initializeDateTimeFormat();

        ContentValues values = new ContentValues();
        values.put(TBL_SYSTEM_ERRORS_PROBLEM_OR_MESSAGE, problemOrMessage);
        values.put(TBL_SYSTEM_ERRORS_TIMESTAMP, this.dateFormat.format(new Date()));

        db.insert(TBL_SYSTEM_ERRORS, null, values);
    }
}

