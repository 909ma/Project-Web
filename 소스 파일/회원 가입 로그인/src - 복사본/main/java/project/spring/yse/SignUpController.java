package project.spring.yse;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignUpController {
	
	  @Autowired
	    private UserService userService;

	    @Autowired
	    private BCryptPasswordEncoder passwordEncoder;

	    // ȸ������ �Է� ȭ��
	    @RequestMapping(value = "/register", method = RequestMethod.GET)
	    public ModelAndView register() {
	        return new ModelAndView("user/signUp");
	    }

	    // ȸ�����
	    @RequestMapping(value = "/register", method = RequestMethod.POST)
	    public ModelAndView createPost(@RequestParam Map<String, Object> map) {
	        ModelAndView mav = new ModelAndView();

	        String userNumber = this.userService.regist(map);
	        if (userNumber == null) {
	            mav.setViewName("redirect:/register"); // ��� ���н� ���� ����Ʈ��
	        } else if (userNumber.equals("Duplicate")) {
	            mav.setViewName("user/signUp"); // �ߺ� ���� �� �ٽ� ȸ������ ��������
	            mav.addObject("errorMessage", "�̹� ��� ���� ���̵� �Ǵ� �г����Դϴ�."); // ��� �޽��� ����
	        } else {
	            mav.setViewName("redirect:/Hello"); // ��� ������ �Ϸ����Ʈ�� �̵�
	        }

	        return mav;
	    }
}