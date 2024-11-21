package com.plane.common.handler;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKBWriter;
import org.locationtech.jts.io.WKTReader;

@MappedTypes(Point.class)
public class PointTypeHandler extends BaseTypeHandler<Point> {
	
	private final GeometryFactory geometryFactory = new GeometryFactory();

	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Point parameter, JdbcType jdbcType)
			throws SQLException {
		
		// Point 객체를 WKT(Well-Known Text) 형식의 문자열로 변환
	    // 예: POINT(127.1234 37.5678)
		if (parameter == null) {
            ps.setNull(i, java.sql.Types.OTHER);
            return;
        }
		
		WKBWriter writer = new WKBWriter();
	    byte[] wkb = writer.write(parameter);
	    ps.setBytes(i, wkb);
	}

	
	@Override
	public Point getNullableResult(ResultSet rs, String columnName) throws SQLException {
		
		// DB에서 WKT 형식의 문자열을 읽어옴
	    String wkt = rs.getString(columnName);
	    if (wkt == null) return null;
	    
	    try {
	        // WKT 문자열을 Point 객체로 변환
	        return (Point) new WKTReader(geometryFactory).read(wkt);
	    } catch (Exception e) {
	        throw new SQLException(e);
	    }
	}

	
	@Override
	public Point getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		
		return getNullableResult(rs, rs.getMetaData().getColumnName(columnIndex));
	}

	
	@Override
	public Point getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String wkt = cs.getString(columnIndex);
		
	    if (wkt == null) return null;
	    
	    try {
	        return (Point) new WKTReader(geometryFactory).read(wkt);
	    } catch (Exception e) {
	        throw new SQLException(e);
	    }
	}

}
