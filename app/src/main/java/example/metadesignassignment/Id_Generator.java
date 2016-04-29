package example.metadesignassignment;

import android.content.Context;
import android.database.Cursor;

import example.metadesignassignment.database.Use_Database;

/**
 * Created by kumargaurav on 29/04/16.
 */
public class Id_Generator {
    private  String Id1 ="Mds-";
    private int Id2=0;
    private String Id3="-aa";

    public Id_Generator(Context context) {
        Use_Database use_database=new Use_Database(context);
        Cursor c=use_database.getdata_table1("");

        Id2 = c.getCount()+1;

    }
    public String getId()
    {
        String Id=Id1+String.valueOf(Id2)+Id3;
        return Id;
    }
}
