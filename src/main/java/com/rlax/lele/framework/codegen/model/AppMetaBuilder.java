package com.rlax.lele.framework.codegen.model;

import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.generator.ColumnMeta;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import io.jboot.utils.StringUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * 自定义 MetaBuilder，处理表前缀跳过生成
 * @author Rlax
 *
 */
public class AppMetaBuilder extends MetaBuilder {
    /**
     * 需要跳过生成的表前缀
     */
    private String[] skipPre = null;

    public AppMetaBuilder(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected boolean isSkipTable(String tableName) {
        if (StringUtils.isNotBlank(skipPre)) {
            for (String skip : skipPre) {
                if (tableName.startsWith(skip)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void setSkipPre(String... skipPre) {
        this.skipPre = skipPre;
    }

    @Override
    protected ResultSet getTablesResultSet() throws SQLException {
        String schemaPattern = dialect instanceof OracleDialect ? dbMeta.getUserName() : null;
        return dbMeta.getTables(conn.getCatalog(), schemaPattern, null, new String[]{"TABLE", "VIEW"});
    }

    @Override
    protected void buildPrimaryKey(TableMeta tableMeta) throws SQLException {
        if (tableMeta.name.toLowerCase().endsWith("_view")) {
            tableMeta.primaryKey = "id";
        } else {
            super.buildPrimaryKey(tableMeta);
        }
    }

    @Override
    protected void buildColumnMetas(TableMeta tableMeta) throws SQLException {
        super.buildColumnMetas(tableMeta);

        String sql = "show full columns from " + tableMeta.name;
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);

        while (rs.next()) {
            for (ColumnMeta col : tableMeta.columnMetas) {
                if (col.name.equals(rs.getString("Field"))) {
                    col.remarks = rs.getString("Comment");
                }
            }
        }

        rs.close();
        stm.close();
    }
}
