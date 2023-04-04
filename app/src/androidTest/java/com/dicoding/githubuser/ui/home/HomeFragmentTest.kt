//package com.dicoding.githubuser.ui.home
//
//import android.util.Log
//import androidx.cardview.widget.CardView
//import androidx.fragment.app.testing.launchFragmentInContainer
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions.*
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.*
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.dicoding.githubuser.MainActivity
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class HomeFragmentTest {
//    private val dummyUsername = "oxychan"
//
//    @Before
//    fun setup() {
//        val scenario = launchFragmentInContainer<HomeFragment>()
////        ActivityScenario.launch(MainActivity::class.java)
//    }
//
//    @get:Rule
//    var activityRule = ActivityScenarioRule(MainActivity::class.java)
//
//    @Test
//    fun testSearchUser() {
//        Log.d("Ndut", "ga ketemu")
//        onView(withId(com.dicoding.githubuser.R.id.sv_user))
//            .perform(typeText(dummyUsername))
//            .perform(pressImeActionButton())
//
//        onView(withId(com.dicoding.githubuser.R.id.pb_user)).check(matches(isDisplayed()))
//
//        val userRecyclerView = onView(withId(com.dicoding.githubuser.R.id.rv_user))
//
//        userRecyclerView.check(matches(isDisplayed()))
//        userRecyclerView.check(matches(hasChildCount(1)))
//        userRecyclerView.check(
//            matches(
//                hasDescendant(
//                    isAssignableFrom(CardView::class.java)
//                )
//            )
//        )
//        userRecyclerView.check(
//            matches(
//                hasDescendant(
//                    withText(dummyUsername)
//                )
//            )
//        )
//    }
//}