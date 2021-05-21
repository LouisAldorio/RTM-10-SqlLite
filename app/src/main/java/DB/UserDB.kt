package DB

import android.provider.BaseColumns

object UserDB {

    class userTable: BaseColumns {
        companion object {
            val TABLE_USER = "user"
            val COLUMN_ID: String = "_id"
            val COLUMN_NAME: String = "nama"
            val COLUMN_EMAIL: String = "email"
            val COLUMN_PHONE: String = "phone"
        }
    }
}