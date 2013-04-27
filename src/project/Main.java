package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import logic.Deserializador;
import logic.Serializador;

import logic.SystemLogic;
import logic.User;
import logic.Session;
import logic.BabyName;

public class Main{
	private static SystemLogic sys = new SystemLogic();
	private static Serializador s = new Serializador();
	private static Deserializador d = new Deserializador();
	public static void main(String[] args) throws Exception{
		final int CAD = 1;
		final int LOG = 2;
		final int RMV = 3;
		int escolha = 0;
		try{
			sys.setUsers(d.deserializa());
		}catch(Exception e){
		}
		
		
		while (escolha != 4){
		String menu = "Digite a sua opcao:\n1: Cadastrar usuario\n2: Fazer login\n3: Remover usuario" +
				"\n4: Sair";
		System.out.println(menu);
		escolha = verificaEntrada();
		switch(escolha){
			case(CAD):{
				List<String> cad = autenticacao();
				User user = null;
				try{
					user = new User(cad.get(0), cad.get(1), cad.get(2));	
					sys.registersUser(user);
					s.Serealizar(sys.getUsers());

				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
				System.out.println("Usuario cadastrado.");
				break;
			}
			case(LOG):{
				List<String> login = new ArrayList<String>();
				
				System.out.println("Digite seu email: ");
				Scanner sc = new Scanner(System.in);
				String email = sc.next();
				System.out.println("Digite sua senha: ");
				String senha = sc.next();
				login.add(email);
				login.add(senha);
				
				User user = null;
				
				try{
					user = sys.login(login.get(0), login.get(1));
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
				if (user == null){
					System.out.println("Usuario invalido!");
					break;
				}
				iniciaPrograma(user);
				s.Serealizar(sys.getUsers());
				break;
			}
			case(RMV):{
				List<String> remove = autenticacao();
				try{
					User user = new User(remove.get(0), remove.get(1), remove.get(2));
					sys.removeUser(user);
					s.Serealizar(sys.getUsers());
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
				System.out.println("Usuario removido.");
				break;
			}
			default:{
				s.Serealizar(sys.getUsers());
				break;
			}
		}
		}
	}

	public static List<String> autenticacao(){
		Scanner sc = new Scanner(System.in);
		List<String> login = new ArrayList<String>();
		System.out.println("Digite seu nome: ");
		String nome = sc.nextLine();
		System.out.println("Digite seu email: ");
		String email = sc.next();
		System.out.println("Digite sua senha: ");
		String senha = sc.next();
		login.add(nome);
		login.add(email);
		login.add(senha);
		return login;
	}
	
	private static int verificaEntrada(){
		Scanner sc = new Scanner(System.in);
		while (!sc.hasNextInt()){
			System.out.println("Valor invalido, digite novamente!");
			sc.next();
		}
		return sc.nextInt();
	}
	
	private static void setaListas(Session session, List<String> lista, int g){
		Set<String> emails  = session.getNames().keySet();
		for (String email : emails){
			session.getNames().put(email, constroiLista(lista, g));
		}
	}
	
	private static void setaListasSR(Session session){
		Set<String> emails  = session.getNames().keySet();
		for (String email : emails){
			session.getNames().put(email, sys.compareGrades(session.getNames()));
		}
	}
	public static void iniciaPrograma(User user){
		String menu = "Digite a opcao desejada:\n1:Iniciar sessao\n2:Abrir Sessao\n3:Sair";
		System.out.println(menu);
		Scanner sc = new Scanner(System.in);
		int escolha = verificaEntrada();
		System.out.println("Nome da sessao: ");
		String id = sc.nextLine();
		
		switch(escolha){
			case(1):{
				System.out.println("Quantidade de participantes da sessao: ");
				int p = verificaEntrada();
				Session s = iniciaSessao(user,id,p);
				if(s == null)
					break;
				switch(primeiraRodada(s,p)){
					case(1):{
						segundaRodada(s);
						break;
					}
					case(2):{
						break;
					}
					default:{
						break;
					}
				}
				break;
			}
			case(2):{

				Session s = null;
				try{
					s = openSession(user,id);
					
				if(sys.sessionName(s) !=null){
					System.out.println("Sessao finaliza,nome escolhido: "+sys.sessionName(s));
					break;
					}
						
					System.out.println("1: Continuar primeira rodada\n2: Finalizar sessao");
					switch(verificaEntrada()){
						case(1):{
							switch(atribuiNotas(s, s.getNames().get(user.getEmail()),s.getNames().size())){
								case(1):{
									segundaRodada(s);
									break;
								}
								default:{
									break;
								}
							}
							break;
						}
						case(2):{
							segundaRodada(s);
							break;
						}
					}
				}
				catch(Exception e){
					System.out.println(e.getMessage());
					iniciaPrograma(user);
				}
				break;
			}
			default:{
				break;
			}
		}
	}
	
	public static Session openSession(User user, String id) throws Exception{
		for (Session s : user.getSessions()){
			if (s.getId().equals(id)){
				return s;
			}
		}
		throw new Exception("Sessao nao encontrada.");
	}
	
	public static Session iniciaSessao(User user,String id,int p){
		Scanner sc = new Scanner(System.in);

		List<String> participantes = new ArrayList<String>();
		String email; 
		for (int i = 0; i < p; i++){
			System.out.println("Email do participante: ");
			email = sc.next();
			while (!email.contains("@")){
				System.out.println("Email invalido, digite novamente:");
				email = sc.next();
			}
			participantes.add(email);
		}
		Session session = null;
		try{
			session = sys.createSession(user, participantes, id);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return session;
	}
	
	private static List<BabyName> constroiLista(List<String> lista, int g){
		
		List<BabyName> nomes = new ArrayList<BabyName>();
		try{
			nomes = sys.createListOfNames(g, lista);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return nomes;
	}
	
	public static int primeiraRodada(Session session,int p){
		System.out.println("Digite o numero correspondente ao genero do bebe:\n0: Meninos\n1: Meninas");
		int g = verificaEntrada();
		System.out.println("Digite os numeros dos tipos de nomes selecionados para a sessao, separados por vнrgulas");
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < sys.getTipos().size(); i++){
			System.out.println((i+1) + ": " + sys.getTipos().get(i).replace(".txt",""));
		}
		String l = sc.next();
		String[] list = l.split(",");
		List<String> lista = new ArrayList<String>();
		for (String tipo : list){
			lista.add(sys.getTipos().get(Integer.parseInt(tipo) - 1));
		}
		List<BabyName> nomes = constroiLista(lista, g);
		setaListas(session, lista, g);
		return atribuiNotas(session, nomes, p);		
	}
	
	private static int atribuiNotas(Session session, List<BabyName> nomes,int p){
		Scanner sc = new Scanner(System.in);
		int nextUser = 3;
		int count = 0; 
		while (nextUser == 3){
			String email;
			email = checaEmail(session.getNames());
			for(int i = 0; i < nomes.size();i++){
				System.out.println(i+1+": "+nomes.get(i).getName());
			}
			int parada = 0;
			while(nomes.size() > parada){
				System.out.println("Digite o numero do nome e sua referente nota (0:ruim, 1:talvez, 2:otimo) separados por virgula ou fim (para nao dar mais nota):");
				String l = sc.next();
				if(l.equals("fim"))
					break;
				String[] list = l.split(",");
				try {
					session.getNames().get(email).get(Integer.valueOf(list[0])-1).setGrade(Integer.valueOf(list[1]));
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}parada++;
			}
			
			count ++;
			if(count < p){
				System.out.println("1: Finalizar 2: Salvar 3: Outro participante");
				nextUser = verificaEntrada();}
			if (count == p){
				System.out.println("1: Finalizar 2: Salvar");
				nextUser = verificaEntrada();
				break;	
			}
				
			}
			
		return nextUser;
		}
		
	
	
	private static String checaEmail(HashMap<String, List<BabyName>> emails){
		String email;      
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite seu email:");
		email = sc.next();
		while (!emails.containsKey(email)){
			System.out.println("Email invalido, digite novamente:");
			email = sc.next();
		}
		return email;		
	}
	
	public static void segundaRodada(Session session){
		String email;
		Scanner sc = new Scanner(System.in);
		List<BabyName> listaDeNomes = sys.compareGrades(session.getNames());
		setaListasSR(session);
		
		int nextUser = 2;
		int count = 0;
		while (listaDeNomes.size() > 1){
			email = checaEmail(session.getNames());
			
			for(BabyName name : listaDeNomes){
				System.out.println(name.getName());				
			}

			System.out.println("Digite a ordem na sua opiniao de acordo com a posicao dos nomes da lista:");
			String l = sc.next();
			String[] list = l.split(",");
			for (int j = 0; j < list.length; j++){
				try{
					listaDeNomes.get(j).setGrade(Integer.parseInt(list[j]));
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
			
			session.getNames().put(email, listaDeNomes);	
			
			System.out.println("1: Finalizar 2: Outro participante");
			nextUser = verificaEntrada();
			count ++;
			if (count == session.getNames().size()){
				break;
			}
			
			}
			
	
		try{
			System.out.println("O nome escolhido foi " + sys.findTheMostPopular(session.getNames()).getName() + "\n" + session.toString());
			HashMap<String, List<BabyName>> finalizada = new HashMap<String, List<BabyName>>();
			List<BabyName> maisPopular = new ArrayList<BabyName>();
			maisPopular.add(sys.findTheMostPopular(session.getNames()));
			finalizada.put("final@", maisPopular);
			session.setNames(finalizada);			
		}
		catch(Exception e){
			System.out.println("Nenhum nome foi escolhido!");
		}
	}	
}

