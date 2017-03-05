package co.com.tactusoft.crm.util;

import org.asteriskjava.live.AsteriskChannel;
import org.asteriskjava.live.LiveException;
import org.asteriskjava.live.OriginateCallback;

public class OriginateCallbackImpl implements OriginateCallback {

	@Override
	public void onBusy(AsteriskChannel arg0) {
		System.out.println("Ocupado");
	}

	@Override
	public void onDialing(AsteriskChannel arg0) {
		System.out.println("Llamando");
	}

	@Override
	public void onFailure(LiveException arg0) {
		System.out.println("Failure");
	}

	@Override
	public void onNoAnswer(AsteriskChannel arg0) {
		System.out.println("No Contesta");
	}

	@Override
	public void onSuccess(AsteriskChannel arg0) {
		System.out.println("Contesta");
	}

}
