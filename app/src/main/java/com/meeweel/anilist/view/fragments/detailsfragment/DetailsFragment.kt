package com.meeweel.anilist.view.fragments.detailsfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.google.android.material.snackbar.Snackbar
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.DetailsFragmentBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.repository.LocalRepositoryImpl
import com.meeweel.anilist.model.room.App
import com.meeweel.anilist.navigation.CustomRouter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class DetailsFragment(
    private val repository: LocalRepository = LocalRepositoryImpl(App.getEntityDao())
) : Fragment() {

    lateinit var ruView: TextView
    lateinit var anime: ShortAnime
//    private val imageMaker: ImageMaker = ImageMaker()
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val router: CustomRouter = App.appRouter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ruView = binding.russianTitle
        arguments?.getParcelable<ShortAnime>(BUNDLE_EXTRA)?.let { anime ->
            this.anime = anime
            populateData(repository.getAnimeById(anime.id))
        }
        binding.detailsScrollView.setOnTouchListener(object: OnSwipeTouchListener(requireContext()) {
            override fun onSwipeLeft() {

            }
            override fun onSwipeRight() {
                router.exit()
            }
        })
        binding.detailsToolbar.menu.findItem(R.id.upload).setOnMenuItemClickListener {
            val api = (requireActivity().application as App).animeApi
            api.getAnime(anime.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    repository.updateFromNetwork(it, anime.id)
//                    Thread {
//                        Glide.get(binding.animeImage.context).clearDiskCache()
//                    }.start()
//                    Glide.get(binding.animeImage.context).clearMemory()
//                    Glide.with(this).load(it.image).diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true)
//                        .into(binding.animeImage)
//                        .load(it.image)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE )
//                        .skipMemoryCache(true)
//                        .into(binding.animeImage)
//                    Glide.with(requireContext())
//                        .load(it.image)
//                        .signature(ObjectKey(System.currentTimeMillis().toString()))
//                        .into(binding.animeImage)
                    populateData(repository.getAnimeById(anime.id))
                    toast("Uploaded")

                }, {
                    toast("No internet")
                })

            toast("Upload")
            true
        }
        binding.detailsToolbar.menu.findItem(R.id.share_to).setOnMenuItemClickListener {
            //Toast.makeText(requireContext(), "Share to", Toast.LENGTH_SHORT).show()
            val drawer = BottomShareDrawer()
            val bundle = Bundle()
            bundle.putInt("aniId", anime.id)
            drawer.arguments = bundle
            drawer.show(requireActivity().supportFragmentManager, "tag")
            true

        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateData(animeData: Anime) {
        with(binding) {

            Glide.with(this.detailsDescriptionImage.context)
                .load(animeData.image)
                .error(R.drawable.anig)
                .into(this.detailsDescriptionImage)
            Glide.with(this.detailsBarImage.context)
                .load(animeData.image)
                .error(R.drawable.anig)
                .into(this.detailsBarImage)
            Glide.with(this.animeImage.context)
                .load(animeData.image)
                .error(R.drawable.anig)
                .into(this.animeImage)

//            binding.detailsBarImage.setImageBitmap(imageMaker.getPictureFromDirectory(animeData.image))
//            binding.detailsDescriptionImage.setImageBitmap(imageMaker.getPictureFromDirectory(animeData.image))
//            animeImage.setImageBitmap(imageMaker.getPictureFromDirectory(animeData.image))

            originalTitle.text = animeData.originalTitle
            englishTitle.text = animeData.enTitle
            russianTitle.text = animeData.ruTitle
            russianTitle.visibility = View.VISIBLE // if (isRussian) View.VISIBLE else View.GONE
            descriptionValue.text = animeData.description
            releaseAuthor.text = "${getText(R.string.author)}: ${animeData.author}"
            releaseGenre.text = "${getText(R.string.genre)}: ${animeData.genre}"
            releaseData.text = "${getText(R.string.data)}: ${animeData.data}"
            releaseAgeRate.text = "${getText(R.string.age_rating)}: ${animeData.ageRating}+"
            releaseRating.text = "${getText(R.string.rating)}: ${animeData.rating}%"
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "anime"

        fun newInstance(animeData: ShortAnime): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE_EXTRA, animeData)
            }
            return fragment
        }
    }

    private fun toast(text: String) {
        val snackBar = Snackbar.make(binding.detailsBar, text, Snackbar.LENGTH_SHORT)
        snackBar.setAction("SKIP") {
//            Toast.makeText(getContext(), "Ok...", Toast.LENGTH_SHORT).show()
        }
        snackBar.show()
    }
}