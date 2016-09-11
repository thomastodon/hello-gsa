package dialects;

import org.hibernate.dialect.MySQL5Dialect;

public class MySQL5MyISAMDialect extends MySQL5Dialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=MyISAM";
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }
}

// Note:
// The older TYPE option was synonymous with ENGINE. TYPE was deprecated in MySQL 4.0 and
// removed in MySQL 5.5. When upgrading to MySQL 5.5 or later, you must convert existing
// applications that rely on TYPE to use ENGINE instead.