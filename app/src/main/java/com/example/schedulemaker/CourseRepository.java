package com.example.schedulemaker;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseRepository {
    private CourseDAO mCourseDao;
    private LiveData<List<Course>> mAllCourses;

    // Note that in order to unit test the CourseRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    CourseRepository(Application application) {
        CourseRoomDatabase db = CourseRoomDatabase.getDatabase(application);
        mCourseDao = db.courseDAO();
        mAllCourses = mCourseDao.getAllCourses();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Course Course) {
        CourseRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.insert(Course);
        });
    }

}
