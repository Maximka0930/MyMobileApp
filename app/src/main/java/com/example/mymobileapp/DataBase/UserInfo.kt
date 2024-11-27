import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class UserInfo(
    val id: Int = -1,
    val email: String? = null,
    val password: String? = null,
    val surname: String? = null,
    val name: String? = null,
    val patronymic: String? = null,
    @Contextual val date_of_birth: String? = null,
    val gender: String? = null,
    val driver_license_number: String? = null,
    @Contextual val date_of_issue_dl: String? = null,
    @Contextual val date_of_registration: String? = null

    )
