import com.example.carecallapp.data.api.WebServices
import com.example.carecallapp.data.repository.data_sources.remote_data_source.RemoteDataSource
import com.example.carecallapp.domain.model.hospital_accounts.Accounts
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val webServices: WebServices):RemoteDataSource{
    override fun getAccounts(sourceId: String?, token: String?): List<Accounts> {
        return emptyList()
    }

}
