package proyecto_base_datos_ii.Utilities;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
public class Proc_manager {

    private SqlTypeMap valDataTypes;
    private DatabaseMetaData metaData;
    private String[] ioDataType = {"UNKNOW","IN","INOUT","OUT","RETORNO","RESULSET"};


    public Proc_manager(Connection connection) throws SQLException {
        this.valDataTypes = new SqlTypeMap();
        this.metaData = connection.getMetaData();
    }

    public List<Function> captureFuncData(String schema) throws SQLException  {
        ResultSet funcs = metaData.getFunctions(null, schema, "%");
        List<Function> fList = new ArrayList<>();

        while (funcs.next()) {
            List<Param> fparams = new ArrayList<>();
            String currFunc = funcs.getString("FUNCTION_NAME");
            String retType = valDataTypes.getType(funcs.getInt("FUNCTION_TYPE"));

            ResultSet params = metaData.getFunctionColumns(null, schema, currFunc, "%");
            while (params.next()) {
                String pname = params.getString("COLUMN_NAME");
                String ptype = ioDataType[params.getInt("COLUMN_TYPE")];
                String pdtype = valDataTypes.getType(params.getInt("DATA_TYPE"));
                fparams.add(new Param(pname, ptype, pdtype));
            }
            params.close();
            fList.add(new Function(currFunc, retType, fparams));
        }
        funcs.close();
        return fList;
    }

    public List<Procedure> captureProcData(String schema) throws SQLException  {
        ResultSet procs = metaData.getProcedures(null, schema, "%");
        List<Procedure> pList = new ArrayList<>();

        while (procs.next()) {
            List<Param> pParams = new ArrayList<>();
            String currProc = procs.getString("PROCEDURE_NAME");
            String retType = valDataTypes.getType(procs.getInt("PROCEDURE_TYPE"));

            ResultSet params = metaData.getProcedureColumns(null, schema, currProc, "%");
            while (params.next()) {
                String pname = params.getString("COLUMN_NAME");
                String ptype = ioDataType[params.getInt("COLUMN_TYPE")];
                String pdtype = valDataTypes.getType(params.getInt("DATA_TYPE"));
                pParams.add(new Param(pname, ptype, pdtype));
            }
            params.close();
            pList.add(new Procedure(currProc, retType, pParams));
        }
        procs.close();
        return pList;
    }


}
