/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.example2.actions;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.example2.http.CPaaSAPI;
import service.example2.http.CPaaSWorkShopApi;
import service.example2.util.InboundXML;

/**
 *
 * @author umansilla
 */
public class WorkShopExample2Actions {
    	private final HttpServletResponse response;
	private final HttpServletRequest request;

	public WorkShopExample2Actions(HttpServletResponse response, HttpServletRequest request) {
		this.response = response;
		this.request = request;
	}

	public void analizarYResponderNivelUnoIVRExampleB() throws IOException {
		System.out.println("analizarYResponderPrimerNivelUnoIVRExampleB");
		if (request.getParameter("Digits") != null) {
			System.out.println(request.getParameter("Digits"));
			switch (request.getParameter("Digits")) {
			case "1":
				System.out.println("case 1");
				response.getWriter().println(InboundXML.setRedirectInboundXML(
						InboundXML.EXAMPLE2_INPUT_NUMERO_DE_CUENTA));
				break;
			case "2":
				System.out.println("case 2");
				response.getWriter().println(InboundXML.setRedirectInboundXML(
						InboundXML.EXAMPLE2_HORARIOS_DE_ATENCION));
				break;
			case "0":
				System.out.println("case 3");
				response.getWriter().println(InboundXML.setRedirectInboundXML(
						InboundXML.EXAMPLE2_TRANSFERENCIA));
				break;
			default:
				response.getWriter().println(InboundXML
						.setRedirectInboundXML(InboundXML.EXAMPLE2_MENU));
			}
		} else {
			System.out.println("ELSE"); 
			response.getWriter().println(InboundXML
					.setRedirectInboundXML(InboundXML.EXAMPLE2_MENU));
		}
	}

	public void analizarYResponderNivelDosIVRExampleB() throws Exception {
		if (request.getParameter("Digits") != null) {
			String numeroDeCuenta = request.getParameter("Digits");
			// OBTENER USUARIO POR NUMERO DE CUENTA CPAAS WORKSHOP API
			JSONObject jsonObjectResponse = new CPaaSWorkShopApi().obtenerUsuarioPorNumeroDeCuenta(numeroDeCuenta);
			if (jsonObjectResponse.has("status") && jsonObjectResponse.getString("status").equals("ok")) {
				// SETEAMOS EL PIN DEL USUARIO POR NUMERO DE CUENTA
				JSONObject jsonObjectResponsePIN = new CPaaSWorkShopApi()
						.crearNuevoPinRandomAUsuarioPorNumeroDeCuenta(numeroDeCuenta);
				if (jsonObjectResponsePIN.has("status") && jsonObjectResponsePIN.getString("status").equals("ok")) {
					// DEBEMOS VALIDAR SI EL TELEFONO DEL USUARIO ES TELEFONO MOVIL
					JSONObject jsonResponseCarrierLookUp = new CPaaSAPI().getCarrierLookUpPorTelefono(
							jsonObjectResponse.getJSONObject("usuario").getString("telefonomovil"));
					if (jsonResponseCarrierLookUp.has("carrier_lookups")) {
						if (jsonResponseCarrierLookUp.getJSONArray("carrier_lookups").getJSONObject(0)
								.getBoolean("mobile")) {
							// ENVIAMOS SMS AL TELEFONO REGISTRADO
							new CPaaSAPI().sendSMSPinRandom(
									jsonObjectResponsePIN.getJSONObject("pinRandomModel").getString("pinRandom"),
									jsonObjectResponse.getJSONObject("usuario").getString("telefonomovil"));
							// RESPONSEMOS AGREGANDO COMO PARAMETRO EL NUMERO DE CUENTA
							response.getWriter().println(InboundXML.setRedirectInboundXML(InboundXML
									.setNumeroDeCuentaEnXML(numeroDeCuenta)));
						} else {
							System.out.println("El telefono registado como movil no es realmente movil");
							response.getWriter()
									.println(InboundXML.setRedirectInboundXML(InboundXML.EXAMPLE2_ERROR_CON_CAUSA,
											"El teléfono movil registrado no es un teléfono movil"));
						}

					} else {
						System.out.println("Error en la petición HTTP getCarrierLookUpPorTelefono");
						response.getWriter().println(InboundXML.setRedirectInboundXML(InboundXML.EXAMPLE2_ERROR_CON_CAUSA,
								"Error en la aplicación"));
					}

				} else {
					System.out.println("Error en la petición HTTP crearNuevoPinRandomAUsuarioPorNumeroDeCuenta");
					response.getWriter().println(InboundXML
							.setRedirectInboundXML(InboundXML.EXAMPLE2_MENU));
				}
			} else {
				//CUANDO NO EXISTE EL NUMERO DE CUENTA
				System.out.println("Error en la petición HTTP obtenerUsuarioPorNumeroDeCuenta");
				response.getWriter().println(InboundXML
						.setRedirectInboundXML(InboundXML.EXAMPLE2_MENU));
			}

		} else {
			response.getWriter().println(InboundXML
					.setRedirectInboundXML(InboundXML.EXAMPLE2_MENU));
		}
	}

	public void analizarYResponderNivelTresIVRExampleB() throws IOException {
		if (request.getParameter("Digits") != null && request.getParameter("numerodecuenta") != null) {
			// OBTENER USUARIO POR NUMERO DE CUENTA CPAAS WORKSHOP API
			JSONObject jsonObjectResponse = new CPaaSWorkShopApi()
					.obtenerUsuarioPorNumeroDeCuenta(request.getParameter("numerodecuenta"));
			if (jsonObjectResponse.has("status") && jsonObjectResponse.getString("status").equals("ok")) {
				// String pinRandom = request.getParameter("Digits");
				// VALIDAMOS QUE EL PIN CORRESPONDA AL NUMERO DE CUENTA
				JSONObject jsonObjectResponsePinValidation = new CPaaSWorkShopApi().validarElPinRandomPorNumeroDeCuenta(
						request.getParameter("numerodecuenta"), request.getParameter("Digits"));
				if (jsonObjectResponsePinValidation.has("status")
						&& jsonObjectResponsePinValidation.getString("status").equals("ok")) {

					if (jsonObjectResponsePinValidation.getJSONObject("pinRandomModel").getString("estatusPin")
							.equals("VALID")) {
						response.getWriter().println(InboundXML.setRedirectInboundXML(
								InboundXML.EXAMPLE2_SALDO_ACTUAL
										+ "?nombreUsuario="
										+ jsonObjectResponse.getJSONObject("usuario").getString("nombre")
										+ "&saldoActual="
										+ jsonObjectResponse.getJSONObject("usuario").getString("saldoactual")));
					} else {
						System.out.println("El telefono registado como movil no es realmente movil");
						response.getWriter().println(InboundXML.setRedirectInboundXML(InboundXML.EXAMPLE2_ERROR_CON_CAUSA,
								"El Pin no corresponse con el pin enviado, favor de intentar nuevamente."));
					}
				} else {
					response.getWriter().println(InboundXML
							.setRedirectInboundXML(InboundXML.EXAMPLE2_MENU));
				}
			} else {
				System.out.println("El telefono registado como movil no es realmente movil");
				response.getWriter().println(InboundXML.setRedirectInboundXML(InboundXML.EXAMPLE2_ERROR_CON_CAUSA,
						"No Ha dado su numero de cuenta."));
			}

		} else {
			response.getWriter().println(InboundXML
					.setRedirectInboundXML(InboundXML.EXAMPLE2_MENU));
		}
	}
}
