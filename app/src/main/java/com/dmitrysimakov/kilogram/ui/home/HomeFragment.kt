package com.dmitrysimakov.kilogram.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dmitrysimakov.kilogram.data.local.entity.Photo
import com.dmitrysimakov.kilogram.data.local.relation.MeasurementWithPreviousResults
import com.dmitrysimakov.kilogram.databinding.FragmentHomeBinding
import com.dmitrysimakov.kilogram.ui.common.measurements.MeasurementsAdapter
import com.dmitrysimakov.kilogram.ui.home.HomeFragmentDirections.Companion.toCalendarDayDialog
import com.dmitrysimakov.kilogram.ui.home.HomeFragmentDirections.Companion.toCreateTrainingFragment
import com.dmitrysimakov.kilogram.ui.home.HomeFragmentDirections.Companion.toPhotoFragment
import com.dmitrysimakov.kilogram.ui.home.HomeFragmentDirections.Companion.toPhotosFragment
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.toIsoString
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.WeekFields
import java.io.File
import java.io.IOException
import java.util.*

private const val RC_TAKE_PHOTO = 1

class HomeFragment : Fragment() {
    
    private val vm: HomeViewModel by viewModel()
    
    private lateinit var binding: FragmentHomeBinding
    
    private val calendarMonthBinder by lazy { CalendarMonthBinder() }
    private val calendarDayBinder by lazy { CalendarDayBinder(resources) {
        navigate(toCalendarDayDialog(it.toIsoString()))
    }}
    private val photosAdapter by lazy { PhotosAdapter{ navigate(toPhotoFragment(it.uri)) } }
    private val measurementsAdapter by lazy { MeasurementsAdapter() }
    
    private var lastPhotoDateTime = OffsetDateTime.now()
    private var lastPhotoUri = ""
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.vm = vm
        binding.photosRV.adapter = photosAdapter
        binding.measurementsRV.adapter = measurementsAdapter
        setupCalendar()
        binding.lifecycleOwner = this
        return binding.root
    }
    
    private fun setupCalendar() {
        binding.apply {
            calendarView.apply {
                dayBinder = calendarDayBinder
                monthHeaderBinder = calendarMonthBinder
                monthScrollListener = { month ->
                    val formatter = DateTimeFormatter.ofPattern("LLLL yyyy г.")
                    dateLabel.text = month.yearMonth.format(formatter).capitalize()
                }
        
                val currentMonth = YearMonth.now()
                val firstMonth = YearMonth.of(2000, 1)
                val lastMonth = currentMonth.plusMonths(1)
                val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
                setup(firstMonth, lastMonth, firstDayOfWeek)
                scrollToMonth(currentMonth)
            }
            
            prevMonthButton.setOnClickListener {
                calendarView.findFirstVisibleMonth()?.let {
                    calendarView.smoothScrollToMonth(it.yearMonth.previous)
                }
            }
    
            nextMonthButton.setOnClickListener {
                calendarView.findFirstVisibleMonth()?.let {
                    calendarView.smoothScrollToMonth(it.yearMonth.next)
                }
            }
        }
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.fab?.hide()
        
        binding.startTrainingButton.setOnClickListener { navigate(toCreateTrainingFragment(null)) }
        binding.addPhotoButton.setOnClickListener { dispatchTakePictureIntent() }
        binding.photosLabel.setOnClickListener { navigate(toPhotosFragment()) }
        
        vm.trainings.observe(viewLifecycleOwner, Observer {
            calendarDayBinder.submitList(it)
            binding.calendarView.notifyCalendarChanged()
        })
        vm.recentPhotos.observe(viewLifecycleOwner, Observer { photosAdapter.submitList(it) })
        measurementsAdapter.submitList(listOf(
                MeasurementWithPreviousResults(1, "Шея", 37.0, LocalDate.now()),
                MeasurementWithPreviousResults(2, "Бицепс", 36.0, LocalDate.now()),
                MeasurementWithPreviousResults(3, "Предплечье", 30.0, LocalDate.now()),
                MeasurementWithPreviousResults(4, "Грудь", 105.0, LocalDate.now()),
                MeasurementWithPreviousResults(5, "Талия", 75.0, LocalDate.now()),
                MeasurementWithPreviousResults(6, "Таз", 75.0, LocalDate.now()),
                MeasurementWithPreviousResults(7, "Бедро", 75.0, LocalDate.now()),
                MeasurementWithPreviousResults(8, "Голень", 75.0, LocalDate.now())
        ))
    }
    
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also { file ->
                val photoURI: Uri = FileProvider.getUriForFile(
                        context!!,
                        "com.dmitrysimakov.kilogram",
                        file
                )
                lastPhotoUri = photoURI.toString()
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, RC_TAKE_PHOTO)
            }
        }
    }
    
    @Throws(IOException::class)
    private fun createImageFile(): File {
        lastPhotoDateTime = OffsetDateTime.now()
        val filename = "Kilogram_${lastPhotoDateTime}"
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(filename, ".jpg", storageDir)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_TAKE_PHOTO && resultCode == RESULT_OK) {
            vm.addPhoto(Photo(lastPhotoUri, lastPhotoDateTime))
        }
    }
}
