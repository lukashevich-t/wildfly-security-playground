package by.gto.erip.face.arm.producers;

import java.util.function.Supplier;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@ApplicationScoped
public class DsProducer {
	@Produces
	@Named("bla")
	private Supplier<String> produce() {
//		return new Supplier<String>() {
//
//			@Override
//			public String get() {
//				return "bla";
//			}
//		};

		return () -> "bla";
	}
}
