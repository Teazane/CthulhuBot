package bot;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

/* Fonctionnalit�s :
 * D�tecter les mots grossiers et r�pondre un truc marrant en cons�quence
 * R�pondre quand on parle de lui, r�pond � un merci
 * Se tait 15 minutes
 * */
public class PoliteListener implements EventListener {

	@Override
	public void onEvent(Event event) {
		if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent e = (MessageReceivedEvent) event;
            User author = e.getAuthor();
            boolean bot = author.isBot(); 
            String message = e.getMessage().getContent();
            
            if (!bot){ //On ne r�pond que si c'est pas un bot
            	Random r = new Random();
            	
            	// Liste des mots � matcher
	            Pattern merde = Pattern.compile("[Mm][Ee][Rr][Dd][Ee]"); 
	            Pattern con = Pattern.compile(" [Cc]on[s.,]* | [Cc]on[s.!?]*$"); 
	            Pattern cthulhu = Pattern.compile("[Cc]thulhu");
	            Pattern fucking = Pattern.compile("[Ff][Uu][Cc][Kk]");
	            Pattern merci = Pattern.compile("[mM]erci [Cc]thulhu");
	            Pattern silence = Pattern.compile("[Ss]ilence [Cc]thulhu");
	            Pattern tagueule = Pattern.compile("[Tt]a gueule [Cc]thulhu");

	            // R�actions en fonction du match
	            Matcher m = merde.matcher(message);
	            if (m.find( )) {
	            	String randomMotChatie;
	            	int random = r.nextInt(3);
	            	switch (random) {
		            	case 0 :
		            		randomMotChatie = "sacrebleu";
		            		break;
		            	case 1 : 
		            		randomMotChatie = "fl�te";
		            		break;
		            	case 2 :
		            		randomMotChatie = "cornegidouille";
		            		break;
		            	default :
		            		randomMotChatie = "mince";
	            	}	
	                e.getChannel().sendMessage("On dit \""+randomMotChatie+"\" grossier personnage !").queue();
	            }
	            
	            m = con.matcher(message);
	            if (m.find( )) {
	            	String randomMotChatie;
	            	int random = r.nextInt(3);
	            	switch (random) {
		            	case 0 :
		            		randomMotChatie = "soutien de Valls";
		            		break;
		            	case 1 : 
		            		randomMotChatie = "supporter de football";
		            		break;
		            	case 2 :
		            		randomMotChatie = "sympathisant de droite";
		            		break;
		            	default :
		            		randomMotChatie = "b�te";
	            	}	
	                e.getChannel().sendMessage("C'est pas tr�s gentil. On dit \""+randomMotChatie+"\".").queue();
	            }
	            
	            
	            m = cthulhu.matcher(message);
	            if (m.find( )) {
	            	
	            	m = merci.matcher(message);
		            if (m.find()) {
		            	String randomAnswer;
		            	int random = r.nextInt(3);
		            	switch (random) {
			            	case 0 :
			            		randomAnswer = "Les humains m'adorent.";
			            		break;
			            	case 1 : 
			            		String name = author.getName();
			            		randomAnswer = "De rien "+name+" !";
			            		break;
			            	case 2 :
			            		randomAnswer = "A votre service.";
			            		break;
			            	default :
			            		randomAnswer = "De rien.";
		            	}	
		                e.getChannel().sendMessage(randomAnswer).queue();
		            }
		            
		            else {
		            	m = silence.matcher(message);
		            	if (m.find()) {
		            		e.getChannel().sendMessage("Et le silence fut.").queue();
		            		try {
								Thread.sleep(1000*60*15); //attendre 15 minutes
							} catch (InterruptedException e1) {
								System.out.println("Une erreur est survenue quand Cthulhu s'est endormi.");
								e1.printStackTrace();
							}
		            	} else {
		            		m = tagueule.matcher(message);
			            	if (m.find()) {
			            		e.getChannel().sendMessage("Oui bon bah �a va, pas la peine d'�tre d�sagr�able !").queue();
			            		e.getChannel().sendMessage("*boude*").queue();
			            		try {
									Thread.sleep(1000*60*15); //attendre 15 minutes
								} catch (InterruptedException e1) {
									System.out.println("Une erreur est survenue quand Cthulhu s'est mis � bouder.");
									e1.printStackTrace();
								}
			            	} else {
			            		String randomAnswer;
				            	int random = r.nextInt(3);
				            	switch (random) {
					            	case 0 :
					            		randomAnswer = "On parle de moi ?";
					            		break;
					            	case 1 : 
					            		randomAnswer = "Je suis l� !";
					            		break;
					            	case 2 :
					            		randomAnswer = "J'aime le doux son de mon nom...";
					            		break;
					            	default :
					            		randomAnswer = "Hey.";
				            	}	
				                e.getChannel().sendMessage(randomAnswer).queue();
			            	}
		            	}
		            }
	            }
	            
	            m = fucking.matcher(message);
	            if (m.find()) {
	            	e.getChannel().sendMessage("Le... fucking !").queue();
	            }
	            
            }
		}
	}

}