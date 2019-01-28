package svyatoslavbabyak.com.spacex.entity

import android.os.Parcel
import com.google.gson.annotations.SerializedName
import svyatoslavbabyak.com.spacex.utility.*
import java.util.*
import kotlin.collections.ArrayList

data class Launch(
    val details: String?,
    @SerializedName("flight_number")
    val flightNumber: Int,
    @SerializedName("is_tentative")
    val isTentative: Boolean,
    @SerializedName("launch_date_local")
    val launchDateLocal: Date,
    @SerializedName("launch_date_unix")
    val launchDateUnix: Int,
    @SerializedName("launch_date_utc")
    val launchDateUTC: Date,
    @SerializedName("launch_failure_details")
    val launchFailureDetails: LaunchSite.LaunchFailureDetails,
    @SerializedName("launch_site")
    val launchSite: LaunchSite,
    @SerializedName("launch_success")
    val launchSuccess: Boolean?,
    @SerializedName("launch_window")
    val launchWindow: Int?,
    @SerializedName("launch_year")
    val launchYear: String,
    val links: LaunchSite.Links,
    @SerializedName("mission_id")
    val missionId: List<String?>,
    @SerializedName("mission_name")
    val missionName: String,
    val rocket: LaunchSite.Rocket,
    val ships: List<String?>,
    val tbd: Boolean,
    val telemetry: LaunchSite.Telemetry,
    @SerializedName("tentative_max_precision")
    val tentativeMaxPrecision: String,
    val upcoming: Boolean) : KParcelable {

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Launch)
    }

    private constructor(p: Parcel) : this (
        details = p.readString(),
        flightNumber = p.readInt(),
        isTentative = p.readBoolean()!!,
        launchDateLocal = p.readDate()!!,
        launchDateUnix = p.readInt(),
        launchDateUTC = p.readDate()!!,
        launchFailureDetails = p.readTypedObjectCompat(LaunchSite.LaunchFailureDetails.CREATOR)!!,
        launchSite = p.readTypedObjectCompat(LaunchSite.CREATOR)!!,
        launchSuccess = p.readBoolean(),
        launchWindow = p.readValue(Int::class.java.classLoader) as Int?,
        launchYear = p.readString()!!,
        links = p.readTypedObjectCompat(LaunchSite.Links.CREATOR)!!,
        missionId = p.createStringArrayList()!!,
        missionName = p.readString()!!,
        rocket = p.readTypedObjectCompat(LaunchSite.Rocket.CREATOR)!!,
        ships = p.createStringArrayList()!!,
        tbd = p.readBoolean()!!,
        telemetry = p.readTypedObjectCompat(LaunchSite.Telemetry.CREATOR)!!,
        tentativeMaxPrecision = p.readString()!!,
        upcoming = p.readBoolean()!!)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(details)
            writeInt(flightNumber)
            writeBoolean(isTentative)
            writeDate(launchDateLocal)
            writeInt(launchDateUnix)
            writeDate(launchDateUTC)
            writeTypedObjectCompat(launchFailureDetails, flags)
            writeTypedObjectCompat(launchSite, flags)
            writeBoolean(launchSuccess)
            writeValue(launchWindow)
            writeString(launchYear)
            writeTypedObjectCompat(links, flags)
            writeStringList(missionId)
            writeString(missionName)
            writeTypedObjectCompat(rocket, flags)
            writeStringList(ships)
            writeBoolean(tbd)
            writeTypedObjectCompat(telemetry, flags)
            writeString(tentativeMaxPrecision)
            writeBoolean(upcoming)
        }
    }
}

data class LaunchSite(
    @SerializedName("site_id")
    val siteId: String,
    @SerializedName("site_name")
    val siteName: String,
    @SerializedName("site_name_long")
    val siteNameLong: String) : KParcelable {

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::LaunchSite)
    }

    private constructor(p: Parcel) : this(
        siteId = p.readString()!!,
        siteName = p.readString()!!,
        siteNameLong = p.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(siteId)
            writeString(siteName)
            writeString(siteNameLong)
        }
    }

    data class Telemetry(
        @SerializedName("flight_club")
        val flightClub: String?) : KParcelable {

        companion object {
            @JvmField
            val CREATOR = parcelableCreator(::Telemetry)
        }

        private constructor(p: Parcel) : this(
            flightClub = p.readString()
        )

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(flightClub)
        }
    }


    data class LaunchFailureDetails(
        val altitude: Int,
        val reason: String,
        val time: Int) : KParcelable {

        companion object {
            @JvmField
            val CREATOR = parcelableCreator(::LaunchFailureDetails)
        }

        private constructor(p: Parcel) : this(
            altitude = p.readInt(),
            reason = p.readString()!!,
            time = p.readInt()
        )

        override fun writeToParcel(dest: Parcel, flags: Int) {
            with(dest) {
                writeInt(altitude)
                writeString(reason)
                writeInt(time)
            }
        }
    }

    data class Links(
        @SerializedName("article_link")
        val articleLink: String?,
        @SerializedName("flickr_images")
        val flickrImages: ArrayList<String?>,
        @SerializedName("mission_patch")
        val missionPatch: String?,
        @SerializedName("mission_patch_small")
        val missionPatchSmall: String?,
        val presskit: String?,
        @SerializedName("reddit_campaign")
        val redditCampaign: String?,
        @SerializedName("reddit_launch")
        val redditLaunch: String?,
        @SerializedName("reddit_media")
        val redditMedia: String?,
        @SerializedName("reddit_recovery")
        val redditRecovery: String?,
        @SerializedName("video_link")
        val videoLink: String?,
        val wikipedia: String?,
        @SerializedName("youtube_id")
        val youtubeId: String?) : KParcelable {

        companion object {
            @JvmField
            val CREATOR = parcelableCreator(::Links)
        }

        private constructor(p: Parcel) : this(
            articleLink = p.readString(),
            flickrImages = p.createStringArrayList()!!,
            missionPatch = p.readString(),
            missionPatchSmall = p.readString(),
            presskit = p.readString(),
            redditCampaign = p.readString(),
            redditLaunch = p.readString(),
            redditMedia = p.readString(),
            redditRecovery = p.readString(),
            videoLink = p.readString(),
            wikipedia = p.readString(),
            youtubeId = p.readString()
        )

        override fun writeToParcel(dest: Parcel, flags: Int) {
            with(dest) {
                writeString(articleLink)
                writeStringList(flickrImages)
                writeString(missionPatch)
                writeString(missionPatchSmall)
                writeString(presskit)
                writeString(redditCampaign)
                writeString(redditLaunch)
                writeString(redditMedia)
                writeString(redditRecovery)
                writeString(videoLink)
                writeString(wikipedia)
                writeString(youtubeId)
            }
        }
    }


    data class Rocket(
        val fairings: Fairings?,
        @SerializedName("first_stage")
        val firstStage: FirstStage,
        @SerializedName("rocket_id")
        val rocketId: String,
        @SerializedName("rocket_name")
        val rocketName: String,
        @SerializedName("rocket_type")
        val rocketType: String,
        @SerializedName("second_stage")
        val secondStage: SecondStage
    ) : KParcelable {

        companion object {
            @JvmField
            val CREATOR = parcelableCreator(::Rocket)
        }

        private constructor(p: Parcel) : this(
            fairings = p.readTypedObjectCompat(Fairings.CREATOR)!!,
            firstStage = p.readTypedObjectCompat(FirstStage.CREATOR)!!,
            rocketId = p.readString()!!,
            rocketName = p.readString()!!,
            rocketType = p.readString()!!,
            secondStage = p.readTypedObjectCompat(SecondStage.CREATOR)!!
        )

        override fun writeToParcel(dest: Parcel, flags: Int) {
            with(dest) {
                writeTypedObjectCompat(fairings, flags)
                writeTypedObjectCompat(firstStage, flags)
                writeString(rocketId)
                writeString(rocketName)
                writeString(rocketType)
                writeTypedObjectCompat(secondStage, flags)
            }
        }
    }

    data class SecondStage(
        val block: Int?,
        val payloads: List<Payload>
    ) : KParcelable {

        companion object {
            @JvmField
            val CREATOR = parcelableCreator(::SecondStage)
        }

        private constructor(p: Parcel) : this(
            block = p.readValue(Int::class.java.classLoader) as Int?,
            payloads = p.createTypedArrayList(Payload.CREATOR)!!
        )

        override fun writeToParcel(dest: Parcel, flags: Int) {
            with(dest) {
                writeValue(block)
                writeTypedList(payloads)
            }
        }
    }

    data class Payload(
        val customers: ArrayList<String>,
        val manufacturer: String,
        val nationality: String,
        val orbit: String,
        @SerializedName("payload_id")
        val payloadId: String,
        @SerializedName("payload_mass_kg")
        val payloadMassKg: Float?,
        @SerializedName("payload_mass_lbs")
        val payloadMassLbs: Float?,
        @SerializedName("payload_type")
        val payloadType: String,
        val reused: Boolean
    ) : KParcelable {

        companion object {
            @JvmField
            val CREATOR = parcelableCreator(::Payload)
        }

        private constructor(p: Parcel) : this(
            customers = p.createStringArrayList()!!,
            manufacturer = p.readString()!!,
            nationality = p.readString()!!,
            orbit = p.readString()!!,
            payloadId = p.readString()!!,
            payloadMassKg = p.readValue(Float::class.java.classLoader) as Float?,
            payloadMassLbs = p.readValue(Float::class.java.classLoader) as Float?,
            payloadType = p.readString()!!,
            reused = p.readBoolean()!!
        )

        override fun writeToParcel(dest: Parcel, flags: Int) {
            with(dest) {
                writeStringList(customers)
                writeString(manufacturer)
                writeString(nationality)
                writeString(orbit)
                writeString(payloadId)
                writeValue(payloadMassKg)
                writeValue(payloadMassLbs)
                writeString(payloadType)
                writeBoolean(reused)
            }
        }
    }

    data class Fairings(
        val recovered: Boolean,
        @SerializedName("recovery_attempt")
        val recoveryAttempt: Boolean?,
        val reused: Boolean,
        val ship: String?
    ) : KParcelable {

        companion object {
            @JvmField
            val CREATOR = parcelableCreator(::Fairings)
        }

        private constructor(p: Parcel) : this(
            recovered = p.readBoolean()!!,
            recoveryAttempt = p.readBoolean(),
            reused = p.readBoolean()!!,
            ship = p.readString()
        )

        override fun writeToParcel(dest: Parcel, flags: Int) {
            with(dest) {
                writeBoolean(recovered)
                writeBoolean(recoveryAttempt)
                writeBoolean(reused)
                writeString(ship)
            }
        }
    }

    data class FirstStage(
        val cores: List<Core>
    ) : KParcelable {

        companion object {
            @JvmField
            val CREATOR = parcelableCreator(::FirstStage)
        }

        private constructor(p: Parcel) : this(
            cores = p.createTypedArrayList(Core.CREATOR)!!
        )

        override fun writeToParcel(dest: Parcel, flags: Int) {
            with(dest) {
                writeTypedList(cores)
            }
        }
    }

    data class Core(
        val block: Int?,
        @SerializedName("core_serial")
        val coreSerial: String?,
        val flight: Int?,
        val gridfins: Boolean?,
        @SerializedName("land_success")
        val landSuccess: Boolean?,
        @SerializedName("landing_intent")
        val landingIntent: Boolean?,
        @SerializedName("landing_type")
        val landingType: String?,
        @SerializedName("landing_vehicle")
        val landingVehicle: String?,
        val legs: Boolean?,
        val reused: Boolean?
    ) : KParcelable {

        companion object {
            @JvmField
            val CREATOR = parcelableCreator(::Core)
        }

        private constructor(p: Parcel) : this(
            block = p.readValue(Int::class.java.classLoader) as Int?,
            coreSerial = p.readString(),
            flight = p.readValue(Int::class.java.classLoader) as Int?,
            gridfins = p.readBoolean(),
            landSuccess = p.readBoolean(),
            landingIntent = p.readBoolean(),
            landingType = p.readString(),
            landingVehicle = p.readString(),
            legs = p.readBoolean(),
            reused = p.readBoolean()
        )

        override fun writeToParcel(dest: Parcel, flags: Int) {
            with(dest) {
                writeValue(block)
                writeString(coreSerial)
                writeValue(flight)
                writeBoolean(gridfins)
                writeBoolean(landSuccess)
                writeBoolean(landingIntent)
                writeString(landingType)
                writeString(landingVehicle)
                writeBoolean(legs)
                writeBoolean(reused)
            }
        }
    }
}