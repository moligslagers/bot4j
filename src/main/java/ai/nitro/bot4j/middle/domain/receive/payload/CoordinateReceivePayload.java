package ai.nitro.bot4j.middle.domain.receive.payload;

public class CoordinateReceivePayload extends AbstractReceivePayload {

	private double latVal;

	private double lonVal;

	public CoordinateReceivePayload() {
		super(Type.COORDINATE);
	}

	public double getLatVal() {
		return latVal;
	}

	public double getLonVal() {
		return lonVal;
	}

	public void setLatVal(final double latVal) {
		this.latVal = latVal;
	}

	public void setLonVal(final double lonVal) {
		this.lonVal = lonVal;
	}

}
