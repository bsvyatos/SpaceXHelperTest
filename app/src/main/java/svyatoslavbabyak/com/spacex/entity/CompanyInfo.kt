package svyatoslavbabyak.com.spacex.entity

import android.os.Parcel
import com.google.gson.annotations.SerializedName
import svyatoslavbabyak.com.spacex.utility.KParcelable
import svyatoslavbabyak.com.spacex.utility.parcelableCreator
import svyatoslavbabyak.com.spacex.utility.readTypedObjectCompat
import svyatoslavbabyak.com.spacex.utility.writeTypedObjectCompat

data class CompanyInfo(
    val ceo: String,
    val coo: String,
    val cto: String,
    @SerializedName("cto_propulsion")
    val ctoPropulsion: String,
    val employees: Int,
    val founded: Int,
    val founder: String,
    val headquarters: Headquarters,
    @SerializedName("launch_sites")
    val launchSites: Int,
    val name: String,
    val summary: String,
    @SerializedName("test_sites")
    val testSites: Int,
    val valuation: Long,
    val vehicles: Int) : KParcelable {

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::CompanyInfo)
    }

    private constructor(p: Parcel) : this(
        ceo = p.readString()!!,
        coo = p.readString()!!,
        cto = p.readString()!!,
        ctoPropulsion = p.readString()!!,
        employees = p.readInt(),
        founded = p.readInt(),
        founder = p.readString()!!,
        headquarters = p.readTypedObjectCompat(Headquarters.CREATOR)!!,
        launchSites = p.readInt(),
        name = p.readString()!!,
        summary = p.readString()!!,
        testSites = p.readInt(),
        valuation = p.readLong(),
        vehicles = p.readInt()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(ceo)
            writeString(coo)
            writeString(cto)
            writeString(ctoPropulsion)
            writeInt(employees)
            writeInt(founded)
            writeString(founder)
            writeTypedObjectCompat(headquarters, flags)
            writeInt(launchSites)
            writeString(name)
            writeString(summary)
            writeInt(testSites)
            writeLong(valuation)
            writeInt(vehicles)
        }
    }
}

data class Headquarters(
    val address: String,
    val city: String,
    val state: String) : KParcelable {

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::Headquarters)
    }

    private constructor(p: Parcel) : this(
        address = p.readString()!!,
        city = p.readString()!!,
        state =  p.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(address)
            writeString(city)
            writeString(state)
        }
    }
}