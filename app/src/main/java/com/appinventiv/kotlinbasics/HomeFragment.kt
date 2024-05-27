package com.appinventiv.kotlinbasics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appinventiv.kotlinbasics.hilts.FirebaseQualifier
import com.appinventiv.kotlinbasics.hilts.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
//    @Named("sql")
    @FirebaseQualifier
    lateinit var repository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        repository.saveUser("Jai Jai", "Shri Ram")
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}