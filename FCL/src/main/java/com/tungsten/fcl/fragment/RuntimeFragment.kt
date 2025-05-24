package com.tungsten.fcl.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.tungsten.fcl.R
import com.tungsten.fcl.activity.SplashActivity
import com.tungsten.fcl.databinding.FragmentRuntimeBinding
import com.tungsten.fcl.util.RuntimeUtils
import com.tungsten.fclauncher.utils.FCLPath
import com.tungsten.fclcore.task.Schedulers
import com.tungsten.fcllibrary.component.FCLFragment
import java.io.IOException

class RuntimeFragment : FCLFragment(), View.OnClickListener {
    private lateinit var bind: FragmentRuntimeBinding
    var lwjgl: Boolean = false
    var cacio: Boolean = false
    var cacio11: Boolean = false
    var cacio17: Boolean = false
    var jna: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_runtime, container, false)
        bind = FragmentRuntimeBinding.bind(view)
        bind.install.setOnClickListener(this)
        Schedulers.defaultScheduler().execute {
            initState()
            Schedulers.androidUIThread().execute {
                refreshDrawables()
                check()
            }
        }
        return view
    }

    private fun initState() {
        lwjgl = (activity as SplashActivity).lwjgl
        cacio = (activity as SplashActivity).cacio
        cacio11 = (activity as SplashActivity).cacio11
        cacio17 = (activity as SplashActivity).cacio17
        jna = (activity as SplashActivity).jna
    }

    private fun refreshDrawables() {
        if (context != null) {
            val stateUpdate =
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_update_24)
            val stateDone =
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_done_24)

            stateUpdate?.setTint(Color.GRAY)
            stateDone?.setTint(Color.GRAY)

            bind.apply {
                lwjglState.setBackgroundDrawable(if (lwjgl) stateDone else stateUpdate)
                cacioState.setBackgroundDrawable(if (cacio) stateDone else stateUpdate)
                cacio11State.setBackgroundDrawable(if (cacio11) stateDone else stateUpdate)
                cacio17State.setBackgroundDrawable(if (cacio17) stateDone else stateUpdate)
                jnaState.setBackgroundDrawable(if (jna) stateDone else stateUpdate)
            }
        }
    }

    private val isLatest: Boolean
        get() = lwjgl && cacio && cacio11 && cacio17 && jna

    private fun check() {
        if (isLatest) {
            (activity as SplashActivity).enterLauncher()
        }
    }

    private var installing = false

    private fun install() {
        if (installing) return

        bind.apply {
            installing = true
            if (!lwjgl) {
                lwjglState.visibility = View.GONE
                lwjglProgress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.install(context, FCLPath.LWJGL_DIR, "app_runtime/lwjgl")
                        lwjgl = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        lwjglState.visibility = View.VISIBLE
                        lwjglProgress.visibility = View.GONE
                        refreshDrawables()
                        check()
                    }
                }.start()
            }
            if (!cacio) {
                cacioState.visibility = View.GONE
                cacioProgress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.install(
                            context,
                            FCLPath.CACIOCAVALLO_8_DIR,
                            "app_runtime/caciocavallo"
                        )
                        cacio = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        cacioState.visibility = View.VISIBLE
                        cacioProgress.visibility = View.GONE
                        refreshDrawables()
                        check()
                    }
                }.start()
            }
            if (!cacio11) {
                cacio11State.visibility = View.GONE
                cacio11Progress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.install(
                            context,
                            FCLPath.CACIOCAVALLO_11_DIR,
                            "app_runtime/caciocavallo11"
                        )
                        cacio11 = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        cacio11State.visibility = View.VISIBLE
                        cacio11Progress.visibility = View.GONE
                        refreshDrawables()
                        check()
                    }
                }.start()
            }
            if (!cacio17) {
                cacio17State.visibility = View.GONE
                cacio17Progress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.install(
                            context,
                            FCLPath.CACIOCAVALLO_17_DIR,
                            "app_runtime/caciocavallo17"
                        )
                        cacio17 = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        cacio17State.visibility = View.VISIBLE
                        cacio17Progress.visibility = View.GONE
                        refreshDrawables()
                        check()
                    }
                }.start()
            }
            if (!jna) {
                jnaState.visibility = View.GONE
                jnaProgress.visibility = View.VISIBLE
                Thread {
                    try {
                        RuntimeUtils.installJna(
                            context,
                            FCLPath.JNA_PATH,
                            "app_runtime/jna"
                        )
                        jna = true
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    activity?.runOnUiThread {
                        jnaState.visibility = View.VISIBLE
                        jnaProgress.visibility = View.GONE
                        refreshDrawables()
                        check()
                    }
                }.start()
            }
        }
    }

    override fun onClick(view: View) {
        if (view === bind.install) {
            install()
        }
    }
}
