package moonproject.foodmood.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchService {

    @GET("{query}")
    suspend fun search(@Path("query") query: String): List<RemoteSearchData>

}

data class RemoteSearchData(
    @SerializedName("fd_id") val id: Long,
    @SerializedName("name_main_ru") val name: String,
    @SerializedName("name_plus_ru") val description: String?,
    @SerializedName("name_note_ru") val note: String?,
)

/*
* {"fd_id":157589,
* "name_main_ru":"гречка",
* "name_plus_ru":"варёная на воде",
* "name_note_ru":"гречневая каша"}
* */