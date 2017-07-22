package bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.EventListener;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot implements EventListener {

	private JDA jda;
    private boolean stop = false; 
    
    //Pour stopper le bot si besoin
	@Override
	public void onEvent(Event event) {
		if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent e = (MessageReceivedEvent) event;
            String message = e.getMessage().getContent();
            Pattern stopit = Pattern.compile("!stop");
            Matcher m = stopit.matcher(message);
            if (m.find()){ //Si le message contient "!stop"
            	jda.shutdown(true); //On arr�te le bot
                stop = true;
            }
		}
	}
    
	//Param�tre et lance le bot
    public Bot (String token) {
        try {
			jda = new JDABuilder(AccountType.BOT).setToken(token) //Token pass� en param�tre dans le main
					.setBulkDeleteSplittingEnabled(false).buildBlocking();
			jda.addEventListener(this); //Ajout du listener de stop
			jda.addEventListener(new MainListener()); //Ajout du listener d�bile x)
			jda.addEventListener(new PoliteListener()); //Ajout du listener de politesse
			jda.addEventListener(new LivredorListener()); //Ajout des fonctions livre d'or
        } catch (IllegalArgumentException |LoginException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue ; veuillez v�rifier le token ou votre connection internet.");
            return;
        }
        
    	int i; //Nombre de channels sur lequel le bot est actif
        System.out.println("Connect� comme : " + jda.getSelfUser().getName()); //Affichage du nom du bot
        System.out.println("Le bot est autoris� sur " + (i = jda.getGuilds().size()) + " serveur" + (i > 1 ? "s" : "")); //Serveur(s)
        
        List<TextChannel> chan = jda.getTextChannels();
        for (TextChannel c : chan){
        	//System.out.println("Channel - Name:"+c.getName()+" ID:"+c.getId()); //Affiche tous les salons o� il est pr�sent (nom + ID)
        	
        	if (c.getName().equals("general")) //Message de salutation sur general
        		c.sendMessage("Salut tout le monde ! Cthulhu est dans la place ! \n"
        				+ "Pour exp�rimenter mon option livre d'or, tapez \"!helplivredor\" \n"
        				+ "Pour me faire taire, tapez \"Silence Cthulhu\"\n"
        				+ "Pour plus d'info tapez \"!info\"").queue();
        }
        
        while (!stop) { //Tant qu'on ne le stoppe pas
            Scanner scanner = new Scanner(System.in); //Scanne la console
            String cmd = scanner.next(); //Retranscrit en string
            if (cmd.equalsIgnoreCase("stop")) { //Si = stop
                jda.shutdown(true); //On arr�te le bot
                stop = true;
            }
            scanner.close(); //On ferme le scan
        }
    }

    public static void main (String[] args) {
        if (args.length < 1) {
            System.out.println("Veuillez indiquer le token du bot.");
        } else {
            new Bot(args[0]);
        }
    }

	
}