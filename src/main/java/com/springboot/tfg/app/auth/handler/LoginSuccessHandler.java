package com.springboot.tfg.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;
import com.springboot.tfg.app.models.dao.IClienteDAO;
import com.springboot.tfg.app.models.entity.Cliente;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	/*@Autowired
	private IClienteDAO clienteDao;*/
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		
		FlashMap flashMap = new FlashMap();
		
		flashMap.put("success", "Hola " +authentication.getName()+ ", has iniciado sesión con éxito!");
		/*Cliente cliente = clienteDao.findByEmail(authentication.getName());
		flashMap.put("cliente", cliente);*/
		
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		if(authentication != null) {
			logger.info("El usuario '"+authentication.getName()+"' ha iniciado sesión con éxito");
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
