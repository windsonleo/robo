package com.tecsoluction.robo.view;

import java.util.ResourceBundle;

public enum FxmlView {

    INICIAL {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("dashborder.title");
        }

//        @Override
//		public String getFxmlFile() {
//            return "/fxml/dashborder.fxml";
//        }
        
        @Override
		public String getFxmlFile() {
            return "/fxml/inicial.fxml";
        }
    }, 
    
    MAIN {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("dashborder.title");
        }

//        @Override
//		public String getFxmlFile() {
//            return "/fxml/dashborder.fxml";
//        }
        
        @Override
		public String getFxmlFile() {
            return "/fxml/main.fxml";
        }
    }, 
    
    INICIO {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("dashborder.title");
        }

//        @Override
//		public String getFxmlFile() {
//            return "/fxml/dashborder.fxml";
//        }
        
        @Override
		public String getFxmlFile() {
            return "/fxml/inicio.fxml";
        }
    }, 
    
    
    CONFIGURACAO {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("dashborder.title.conf");
        }

//        @Override
//		public String getFxmlFile() {
//            return "/fxml/dashborder.fxml";
//        }
        
        @Override
		public String getFxmlFile() {
            return "/fxml/config.fxml";
        }
    }, 
    
    SPLASH {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("dashborder.title.relatorio");
        }

//        @Override
//		public String getFxmlFile() {
//            return "/fxml/dashborder.fxml";
//        }
        
        @Override
		public String getFxmlFile() {
            return "/fxml/splash.fxml";
        }
    }, 
   
    
    PRELOAD {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/preload.fxml";
        }
    },
    
    FINANCEIRO {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("main.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/financeiro.fxml";
        }
    },
    
    MOVIMENTACAOUSUARIO{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.usuario");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/usuario/movimentacaousuario.fxml";
        }
    },
    
  

    
    
    MOVIMENTACAODETENTO {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.pessoa");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/detento/movimentacaodetento.fxml";
        }
    },
    
  
    CADASTRODETENTO {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.cliente");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/detento/cadastrodetento.fxml";
        }
    },
    
    CADASTROUSUARIO {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.cliente");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/usuario/cadastrousuario.fxml";
        }
    },
    
  
    CADASTROEQUIPAMENTO {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.cliente");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/equipamento/cadastroequipamento.fxml";
        }
    },
    
    MOVIMENTACAOEQUIPAMENTO{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.usuario");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/equipamento/movimentacaoequipamento.fxml";
        }
    },
    
  
    MAPA{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.aluno");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/mapa.fxml";
        }
    },
    
    
    MOVIMENTACAOVETERINARIO{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.usuario");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/veterinario/movimentacaoveterinario.fxml";
        }
    },
    
    MOVIMENTACAOATENDIMENTO{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.usuario");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/atendimento/movimentacaoatendimento.fxml";
        }
    },
    
    INFORMACAOATENDIMENTO{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.aluno");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/atendimento/informacaoatendimento.fxml";
        }
    },
    
    INICIOATENDIMENTO{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.aluno");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/atendimento/inicioatendimento.fxml";
        }
    },
  
    INFORMACAOVETERINARIO{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.aluno");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/veterinario/informacaoveterinario.fxml";
        }
    },
    
    MOVIMENTACAOCONSULTA{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.usuario");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/consulta/movimentacaoconsulta.fxml";
        }
    },
    
  
    INFORMACAOCONSULTA	{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.aluno");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/consulta/informacaoconsulta.fxml";
        }
    },
    
    
    MOVIMENTACAOPET{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.usuario");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/pet/movimentacaopet.fxml";
        }
    },
    
  
    INFORMACAOPRODUTO	{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.aluno");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/produto/informacaoproduto.fxml";
        }
    },
    
    MOVIMENTACAOPRODUTO{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.usuario");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/produto/movimentacaoproduto.fxml";
        }
    },
    
    MOVIMENTACAOPRODUTOCOMPOSTO{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.usuario");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/produtocomposto/movimentacaoprodutocomposto.fxml";
        }
    },
    
    INFORMACAOSERVICO	{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.aluno");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/servico/informacaoservico.fxml";
        }
    },
    
    MOVIMENTACAOSERVICO{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.usuario");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/servico/movimentacaoservico.fxml";
        }
    },
    
  
    INFORMACAOPET{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.aluno");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/pet/informacaopet.fxml";
        }
    },
    
//    MOVIMENTACAOCATEGORIA{
//        @Override
//		public String getTitle() {
//            return getStringFromResourceBundle("mov.usuario");
//        }
//
//        @Override
//		public String getFxmlFile() {
//            return "/fxml/servico/movimentacaoservico.fxml";
//        }
//    },
//    
//  
//    INFORMACAOCATEGORIA{
//        @Override
//		public String getTitle() {
//            return getStringFromResourceBundle("mov.aluno");
//        }
//
//        @Override
//		public String getFxmlFile() {
//            return "/fxml/categoria/informacaocategoria.fxml";
//        }
//    },
  
    
    INFORMACAOUSUARIO{
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.usuario");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/usuario/informacaousuario.fxml";
        }
    },
    
  
    MOVIMENTACAOCONFIGURACAO {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("mov.configuracao");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/configuracao/movimentacaoconfiguracao.fxml";
        }
    },
	
	
	
	GERARXML {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("gxml.titulo");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/gerarxml.fxml";
        }
    };
	
	
	
    
    public abstract String getTitle();
    public abstract String getFxmlFile();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
