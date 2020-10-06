/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.example2.util;

/**
 *
 * @author umansilla
 */
public class InboundXML {
    	/*
	 * INBOUND CALL IVR FINANCE TWO FACTOR AUTHUENTICATION.
	 */
	public final static String EXAMPLE2_MENU = "http://cloud.zang.io/data/inboundxml/67c68af40ca2b7bbae5a4d47564fd9fe80c783f1";
	public final static String EXAMPLE2_INPUT_NUMERO_DE_CUENTA = "http://cloud.zang.io/data/inboundxml/dfe961aeed90d0de45399e988ae2e069ee827bfc";
	public final static String EXAMPLE2_SMS_PIN = "";
	public final static String EXAMPLE2_SALDO_ACTUAL = "";
	public final static String EXAMPLE2_HORARIOS_DE_ATENCION = "";
	public final static String EXAMPLE2_TRANSFERENCIA = "";
	public final static String EXAMPLE2_ERROR_CON_CAUSA = "";
	
	public static String setRedirectInboundXML(String redirectInboundXML, String causa) {
		return "<Response>" + "<Redirect method=\"GET\">" + redirectInboundXML + "?causa=" + causa + "</Redirect>"
				+ "</Response>";
	}
	
	public static String setNumeroDeCuentaEnXML(String numeroDeCuenta) {
		return EXAMPLE2_SMS_PIN +"?numerodecuenta="+ numeroDeCuenta;
	}

	public static String setRedirectInboundXML(String redirectInboundXML) {
		return "<Response>" + "<Redirect method=\"GET\">" + redirectInboundXML + "</Redirect>" + "</Response>";
	}
}
