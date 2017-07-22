package bot;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.*;
import net.dv8tion.jda.core.events.message.*;
import net.dv8tion.jda.core.hooks.EventListener;

/* Fonctionnalit�s :
 * Rep�rer "perd(*)", "jeu(x)" et "game(s)"
 * Rep�rer l'Allemand
 * R�pondre "Pong !" � un ping
 * R�pondre "C'est toujours meilleur qu'une pizza � l'ananas." quand on capte "d�gueulasse"
 * R�pondre al�atoirement "Toi-m�me, 'sp�ce de ..." 1 fois sur 50 o� le "..." est le dernier mot de plus de 3
 * caract�tres de la phrase (en excluant les mots en -er, -ez et -ir)
 * R�pondre "Qu'est-ce que t'as pas compris ?" si "C'est pas faux"
 * R�pondre � un "Bonne nuit"
 * */

public class MainListener implements EventListener {
	
	static int compteMsgBn;
	static final int latenceBn = 15;

	@Override
	public void onEvent(Event event) {
		if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent e = (MessageReceivedEvent) event;
            User author = e.getAuthor();
            boolean bot = author.isBot(); 
            String message = e.getMessage().getContent();
            
            if (!bot){ //On ne r�pond que si c'est pas un bot
            	
            	Random r = new Random();
            	int random = r.nextInt(50);

            	if (compteMsgBn < latenceBn) {
                	compteMsgBn ++; //Nb de messages depuis le dernier "bonne nuit"
            	}
            	
            	
            	
            	// Liste des mots � matcher
	            Pattern ping = Pattern.compile("[Pp][Ii][Nn][Gg]");
	            Pattern jeu = Pattern.compile(" [Jj][Ee][Uu][Xx]?[ .?!]| [Jj][Ee][Uu][Xx]?[.!?]?$|^[Jj][Ee][Uu][Xx]?[ .?!]|^[Jj][Ee][Uu][Xx]?[.?!]?$|[Gg][Aa][Mm][Ee][Ss]? |[Gg][Aa][Mm][Ee][Ss]?$|[Pp][Ee][Rr][Dd][UuRrSs]?");
	            Pattern allemand = Pattern.compile("^[Dd]ie | [Dd]ie |[Kk]artoffeln?|sch�n| [Dd]er |^[dD]er | [Dd]as |^[dD]as |danke|guten?|nacht| ich |^[iI]ch | bin | ein |^[Ee]in ");
	            Pattern degueu = Pattern.compile("[Dd][Ee�][Gg][uU][Ee][Uu]");
	            Pattern toimeme = Pattern.compile("^.* ([A-Za-z���������]{4,}).*$");
	            Pattern pasfaux = Pattern.compile("[Cc]'est pas faux");
	            Pattern info = Pattern.compile("!info");
	            Pattern nuit = Pattern.compile("[Bb]onne nuit");

	            
	            
	            // R�actions en fonction du match
	            Matcher m = ping.matcher(message);
	            if (m.find( )) {
	                e.getChannel().sendMessage("Pong !").queue();
	            }
	            
	            if (random == 0){ // Une fois sur 50 on balance une connerie.
	            	m = toimeme.matcher(message);
	            	if (m.find()){
	            		String wtf = "Toi-m�me, 'sp�ce d";
	            		String suite = m.group(1);
			            Pattern verbe = Pattern.compile("[ei]r|ez$");
			            m = verbe.matcher(suite);
			            if (!m.find()){
			            	Pattern voyelles = Pattern.compile("^[����������aeiouyAEIOUY]");
		            		m = voyelles.matcher(suite);
		            		if (m.find()){
		            			wtf = wtf + "'" + suite + " !";
		            		} else {
		            			wtf = wtf + "e " + suite + " !";
		            		}
		            		e.getChannel().sendMessage(wtf).queue();
			            }
	            	}
	            }
	            
	            m = jeu.matcher(message);
	            if (m.find( )) {
	            	String randomPerdu;
	            	random = r.nextInt(3);
	            	switch (random) {
		            	case 0 :
		            		randomPerdu = "Vous venez de *perdre*. Cordialement.";
		            		break;
		            	case 1 : 
		            		randomPerdu = "C'est dommage de **perdre** !";
		            		break;
		            	case 2 :
		            		randomPerdu = "Vous avez tous perdu ! MUHAHAHAHA !";
		            		break;
		            	default :
		            		randomPerdu = "Perdu.";
	            	}	
	            	
	                e.getChannel().sendMessage(randomPerdu).queue();
	            }
	           
	            m = allemand.matcher(message);
	            if (m.find( )){
	            	String randomGerman;
	            	random = r.nextInt(5);
	            	switch (random) {
		            	case 0 :
		            		randomGerman = "Deutschland �ber alles !";
		            		break;
		            	case 1 :
		            		randomGerman = "Ein Volk, ein Reich, ein Kartoffel !";
		            		break;
		            	case 2 : 
		            		randomGerman = "Godwin would be proud.";
		            		break;
		            	case 3 : 
		            		randomGerman = "Heil Kartoffeln !";
		            		break;
		            	case 4 :
		            		randomGerman = "Achtung !";
		            		break;
		            	default :
		            		randomGerman = "ACHTUNG!";
	            	}
	            	
	            	e.getChannel().sendMessage(randomGerman).queue();
	            }
	            
	            m = degueu.matcher(message);
	            if (m.find( )) {
	                e.getChannel().sendMessage("C'est toujours mieux qu'une pizza � l'ananas.").queue();
	            }
	            
	            m = pasfaux.matcher(message);
	            if (m.find()) {
	            	e.getChannel().sendMessage("Qu'est-ce que t'as pas compris ?").queue();
	            }
	            
	            m = nuit.matcher(message);
	            if (m.find()) {
	            	if (compteMsgBn > latenceBn){ //On regarde si on n'a pas r�agi il y a peu
	      	            compteMsgBn = 0;
	            		if (author.getName().equals("AmyW")){
		            		e.getChannel().sendMessage("Bonne nuit reine des marmottes !");
		            	} else {
			            	e.getChannel().sendMessage("Bonne nuit "+author.getName());
		            	}
	            	}
	            }
	            
	            m = info.matcher(message);
	            if (m.find()) {
	            	e.getChannel().sendMessage("Fonctionnalit�s :\n"
	            			+ "Rep�rer \"perd(*)\", \"jeu(x)\" et \"game(s)\"\n"
	            			+ "Rep�rer l'Allemand\n"
	            			+ "R�pondre � un ping\n"
	            			+ "R�pondre � \"d�gueulasse\"\n"
	            			+ "R�pondre al�atoirement \"Toi-m�me, 'sp�ce de ...\" 1 fois sur 50 o� le \"...\" est le dernier mot de plus de 3 caract�res de la phrase (en excluant les mots en -er, -ez et -ir)\n"
	            			+ "R�pondre � \"C'est pas faux\"\n"
	            			+ "D�tecter les mots grossiers \"con\", \"fuck\" et \"merde\"\n"
	            			+ "R�pondre quand on parle de lui, r�pond � un merci qui lui est adress�\n"
	            			+ "R�pondre � un \"bonne nuit\"\n"
	            			+ "Se tait 15 minutes lors d'un \"Silence Cthulhu\"\n"
	            			+ "Se vexe 15 minutes lors d'un \"Ta gueule Cthulhu\"\n"
	            			+ "Fonctionnalit� livre d'or (tapez \"!helplivredor\" pour plus d'informations)").queue();
	            }
            }   
        }
	}
}
