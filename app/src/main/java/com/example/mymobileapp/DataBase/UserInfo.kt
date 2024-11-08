import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class UserInfo(
    val id: Int = -1,
    val email: String,
    val password: String,
    val surname: String,
    val name: String,
    val patronymic: String,
    @Contextual val date_of_birth: String? = null,
    val gender: String,
    val driver_license_number: String,
    @Contextual val date_of_issue_dl: String? = null,

    )
