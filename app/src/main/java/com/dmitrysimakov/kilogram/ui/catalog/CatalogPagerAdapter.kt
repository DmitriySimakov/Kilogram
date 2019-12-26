package com.dmitrysimakov.kilogram.ui.catalog

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dmitrysimakov.kilogram.ui.catalog.exercises.choose_exercise.Exercises_ChooseExerciseFragment
import com.dmitrysimakov.kilogram.ui.catalog.programs.choose_program.Programs_ChooseProgramFragment

const val EXERCISES_PAGE = 0
const val PROGRAMS_PAGE = 1

class CatalogPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
            EXERCISES_PAGE to { Exercises_ChooseExerciseFragment() },
            PROGRAMS_PAGE to { Programs_ChooseProgramFragment() }
    )
    
    override fun getItemCount() = tabFragmentsCreators.size
    
    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}