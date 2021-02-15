package me.stayplus.myspacex.androidApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import me.stayplus.myspacex.androidApp.databinding.ItemLaunchBinding
import me.stayplus.myspacex.shared.entity.RocketLaunch

class LaunchesAdapter(var launches: List<RocketLaunch>) :
    RecyclerView.Adapter<LaunchesAdapter.LaunchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LaunchViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_launch,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bindData(launches[position])
    }

    override fun getItemCount(): Int {
        return launches.size
    }

    inner class LaunchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemLaunchBinding.bind(view)
        private val missionNameTextView = binding.missionName
        private val launchYearTextView = binding.launchYear
        private val launchSuccessTextView = binding.launchSuccess
        private val missionDetailsTextView = binding.details

        fun bindData(launch: RocketLaunch) {
            val ctx = itemView.context
            missionNameTextView.text =
                ctx.getString(R.string.mission_name_field, launch.missionName)
            launchYearTextView.text =
                ctx.getString(R.string.launch_year_field, launch.launchYear.toString())
            missionDetailsTextView.text =
                ctx.getString(R.string.details_field, launch.details ?: "")
            val launchSuccess = launch.launchSuccess
            if (launchSuccess != null) {
                if (launchSuccess) {
                    launchSuccessTextView.text = ctx.getString(R.string.successful)
                    launchSuccessTextView.setTextColor(
                        (ContextCompat.getColor(
                            itemView.context,
                            R.color.colorSuccessful
                        ))
                    )
                } else {
                    launchSuccessTextView.text = ctx.getString(R.string.unsuccessful)
                    launchSuccessTextView.setTextColor(
                        (ContextCompat.getColor(
                            itemView.context,
                            R.color.colorUnsuccessful
                        ))
                    )
                }
            } else {
                launchSuccessTextView.text = ctx.getString(R.string.no_data)
                launchSuccessTextView.setTextColor(
                    (ContextCompat.getColor(
                        itemView.context,
                        R.color.colorNoData
                    ))
                )
            }
        }
    }

}