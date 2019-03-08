package com.thinkgem.jeesite.modules.thirdApi.customer.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.service.app package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _SetTransport4AppResponse_QNAME = new QName(
			"http://app.service.com/", "setTransport4appResponse");
	private final static QName _GetOuthouse4AppResponse_QNAME = new QName(
			"http://app.service.com/", "getOuthouse4appResponse");
	private final static QName _SetMessage4AppResponse_QNAME = new QName(
			"http://app.service.com/", "setMessage4appResponse");
	private final static QName _SetMessage4App_QNAME = new QName(
			"http://app.service.com/", "setMessage4app");
	private final static QName _SetTransport4App_QNAME = new QName(
			"http://app.service.com/", "setTransport4app");
	private final static QName _GetOuthouse4App_QNAME = new QName(
			"http://app.service.com/", "getOuthouse4app");
	private final static QName _GetEvaluate4App_QNAME = new QName(
			"http://app.service.com/", "getEvaluate4app");
	private final static QName _GetEvaluate4AppResponse_QNAME = new QName(
			"http://app.service.com/", "getEvaluate4appResponse");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: com.service.app
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link GetEvaluate4AppResponse }
	 * 
	 */
	public GetEvaluate4AppResponse createGetEvaluate4AppResponse() {
		return new GetEvaluate4AppResponse();
	}

	/**
	 * Create an instance of {@link SetTransport4AppResponse }
	 * 
	 */
	public SetTransport4AppResponse createSetTransport4AppResponse() {
		return new SetTransport4AppResponse();
	}

	/**
	 * Create an instance of {@link GetEvaluate4App }
	 * 
	 */
	public GetEvaluate4App createGetEvaluate4App() {
		return new GetEvaluate4App();
	}

	/**
	 * Create an instance of {@link GetOuthouse4App }
	 * 
	 */
	public GetOuthouse4App createGetOuthouse4App() {
		return new GetOuthouse4App();
	}

	/**
	 * Create an instance of {@link SetTransport4App }
	 * 
	 */
	public SetTransport4App createSetTransport4App() {
		return new SetTransport4App();
	}

	/**
	 * Create an instance of {@link Outhouse }
	 * 
	 */
	public Outhouse createOuthouse() {
		return new Outhouse();
	}

	/**
	 * Create an instance of {@link AppMessage }
	 * 
	 */
	public AppMessage createAppMessage() {
		return new AppMessage();
	}

	/**
	 * Create an instance of {@link SetMessage4AppResponse }
	 * 
	 */
	public SetMessage4AppResponse createSetMessage4AppResponse() {
		return new SetMessage4AppResponse();
	}

	/**
	 * Create an instance of {@link SetMessage4App }
	 * 
	 */
	public SetMessage4App createSetMessage4App() {
		return new SetMessage4App();
	}

	/**
	 * Create an instance of {@link AppTransport }
	 * 
	 */
	public AppTransport createAppTransport() {
		return new AppTransport();
	}

	/**
	 * Create an instance of {@link GetOuthouse4AppResponse }
	 * 
	 */
	public GetOuthouse4AppResponse createGetOuthouse4AppResponse() {
		return new GetOuthouse4AppResponse();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link SetTransport4AppResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://app.service.com/", name = "setTransport4appResponse")
	public JAXBElement<SetTransport4AppResponse> createSetTransport4AppResponse(
			SetTransport4AppResponse value) {
		return new JAXBElement<SetTransport4AppResponse>(
				_SetTransport4AppResponse_QNAME,
				SetTransport4AppResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link GetOuthouse4AppResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://app.service.com/", name = "getOuthouse4appResponse")
	public JAXBElement<GetOuthouse4AppResponse> createGetOuthouse4AppResponse(
			GetOuthouse4AppResponse value) {
		return new JAXBElement<GetOuthouse4AppResponse>(
				_GetOuthouse4AppResponse_QNAME, GetOuthouse4AppResponse.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link SetMessage4AppResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://app.service.com/", name = "setMessage4appResponse")
	public JAXBElement<SetMessage4AppResponse> createSetMessage4AppResponse(
			SetMessage4AppResponse value) {
		return new JAXBElement<SetMessage4AppResponse>(
				_SetMessage4AppResponse_QNAME, SetMessage4AppResponse.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link SetMessage4App }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://app.service.com/", name = "setMessage4app")
	public JAXBElement<SetMessage4App> createSetMessage4App(SetMessage4App value) {
		return new JAXBElement<SetMessage4App>(_SetMessage4App_QNAME,
				SetMessage4App.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link SetTransport4App }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://app.service.com/", name = "setTransport4app")
	public JAXBElement<SetTransport4App> createSetTransport4App(
			SetTransport4App value) {
		return new JAXBElement<SetTransport4App>(_SetTransport4App_QNAME,
				SetTransport4App.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link GetOuthouse4App }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://app.service.com/", name = "getOuthouse4app")
	public JAXBElement<GetOuthouse4App> createGetOuthouse4App(
			GetOuthouse4App value) {
		return new JAXBElement<GetOuthouse4App>(_GetOuthouse4App_QNAME,
				GetOuthouse4App.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link GetEvaluate4App }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://app.service.com/", name = "getEvaluate4app")
	public JAXBElement<GetEvaluate4App> createGetEvaluate4App(
			GetEvaluate4App value) {
		return new JAXBElement<GetEvaluate4App>(_GetEvaluate4App_QNAME,
				GetEvaluate4App.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link GetEvaluate4AppResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://app.service.com/", name = "getEvaluate4appResponse")
	public JAXBElement<GetEvaluate4AppResponse> createGetEvaluate4AppResponse(
			GetEvaluate4AppResponse value) {
		return new JAXBElement<GetEvaluate4AppResponse>(
				_GetEvaluate4AppResponse_QNAME, GetEvaluate4AppResponse.class,
				null, value);
	}

}
