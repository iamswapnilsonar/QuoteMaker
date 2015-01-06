package com.thuytrinh.quotemaker.viewmodel;

public class DbTable {
  public final String name;
  public final DbField[] fields;
  public final String[] customScripts;

  public DbTable(String name, DbField[] fields, String... customScripts) {
    this.name = name;
    this.fields = fields;
    this.customScripts = customScripts;
  }

  @Override
  public String toString() {
    return name;
  }

  public String getDropSql() {
    return "DROP TABLE IF EXISTS " + name;
  }

  public String getCreateSql() {
    StringBuilder sqlBuilder = new StringBuilder()
        .append("CREATE TABLE ").append(name).append(" (");

    // Ensure that a comma does not appear on the last iteration
    String comma = "";
    for (DbField field : fields) {
      sqlBuilder.append(comma);
      comma = ",";

      sqlBuilder.append(field.name).append(" ").append(field.type).append(" ");

      if (field.constraint != null) {
        sqlBuilder.append(field.constraint);
      }
    }

    sqlBuilder.append(")");
    return sqlBuilder.toString();
  }

  public String[] getFieldNames() {
    String[] fieldNames = new String[fields.length];
    for (int i = 0; i < fields.length; i++) {
      fieldNames[i] = fields[i].name;
    }

    return fieldNames;
  }
}