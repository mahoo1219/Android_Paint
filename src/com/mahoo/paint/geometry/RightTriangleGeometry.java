package com.mahoo.paint.geometry;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class RightTriangleGeometry extends BasicGeometry {
	private float[] vertexs;
	private float[] vertexs_dst;
	private Path path = new Path();

	public RightTriangleGeometry(Paint paint) {
		super(paint);
		vertexs = new float[6];
		vertexs_dst = new float[6];
		vertexs[0] = 0;
		vertexs[1] = 0;
		vertexs[2] = 0;
		vertexs[3] = height;
		vertexs[4] = width;
		vertexs[5] = height;
	}

	@Override
	public void drawGraphic(Canvas canvas) {
		geometryMatrix.mapPoints(vertexs_dst, vertexs);
		path.reset();
		path.moveTo(vertexs_dst[0], vertexs_dst[1]);
		path.lineTo(vertexs_dst[2], vertexs_dst[3]);
		path.lineTo(vertexs_dst[4], vertexs_dst[5]);
		path.close();
		canvas.drawPath(path, paint);
	}

}
