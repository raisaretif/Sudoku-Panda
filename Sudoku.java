import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.Border;

public class Sudoku extends JFrame implements ActionListener{
    int FRAME_HEIGHT;
    int FRAME_WIDTH;
    int FRAME_X_ORIGIN;
    int FRAME_Y_ORIGIN;
    int BUTTON_HEIGHT;
    int BUTTON_WIDTH;

    String[] snumeros;
    String[] ssolucion;
    int[] solucion;
    int[] numeros;
    String[] susuario;
    int[] usuario;
    String nivel;
    Font font1;
    Font font2;
    Border border;
    JTextField k, textos[];
    
    JButton basicoButton, intermedioButton, avanzadoButton, checarButton, giveupButton, inicioButton;
    JLabel background;
    private final JMenu accionMenu;
    
    JMenuItem item;
   
    Container contentPane = getContentPane();
    
    public static void main(String[] args) 
    {
        Sudoku frame = new Sudoku();
        frame.setVisible(true);
    }
    
        public Sudoku()
        {
            int i;
            FRAME_HEIGHT = 700;
            FRAME_WIDTH = 1000;
            FRAME_X_ORIGIN = 150;
            FRAME_Y_ORIGIN = 50;
            BUTTON_HEIGHT = 50;
            BUTTON_WIDTH = 100;
            font1 = new Font("SansSerif", Font.BOLD, 40);
            font2 = new Font("SansSerif", Font.ITALIC, 40);
            border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
             
            setSize(FRAME_WIDTH, FRAME_HEIGHT);
            setResizable(false);
            setTitle("Sudoku Panda");
            setLocation(FRAME_X_ORIGIN, FRAME_Y_ORIGIN);
            contentPane.setLayout(null);
            susuario = new String[81];
            snumeros = new String[81];
            ssolucion = new String[81];
            solucion = new int[81];
            numeros = new int[81];
            usuario = new int[81];
            textos = new JTextField[81];
            
            for(i=0;i<81;i++)
            {
            susuario[i]="";
            }
            
            //Botones Niveles
            basicoButton = new JButton("Básico");
            basicoButton.setBounds(300, 500, BUTTON_WIDTH, BUTTON_HEIGHT);
            contentPane.add(basicoButton);
            basicoButton.addActionListener(this);
            intermedioButton = new JButton("Intermedio");
            intermedioButton.setBounds(450, 500, BUTTON_WIDTH, BUTTON_HEIGHT);
            contentPane.add(intermedioButton);
            intermedioButton.addActionListener(this);
            avanzadoButton = new JButton("Avanzado");
            avanzadoButton.setBounds(600, 500, BUTTON_WIDTH, BUTTON_HEIGHT);
            contentPane.add(avanzadoButton);
            avanzadoButton.addActionListener(this);
          
            //Acciones
            accionMenu = new JMenu("Menú");
            item = new JMenuItem("Inicio");
            item.addActionListener( this );
            accionMenu.add( item );
            accionMenu.addSeparator(); 
            item = new JMenuItem("Salir del juego");
            item.addActionListener( this );
            accionMenu.add( item );
            JMenuBar BarraMenu = new JMenuBar();
            setJMenuBar(BarraMenu);
            BarraMenu.add(accionMenu);
            
            //Fondo
            setLayout(new BorderLayout());
            background=new JLabel(new ImageIcon("images/panda2.jpg"));
            add(background);
            background.setLayout(new FlowLayout());
            
            setDefaultCloseOperation(EXIT_ON_CLOSE);
           }
            
     public void actionPerformed(ActionEvent event) 
     {
        String  MenuName;
        int i, j, g, n, l;
        JPanel grid;
        grid = new JPanel();
        
        if(event.getSource() instanceof JMenuItem)
        {
            MenuName = event.getActionCommand();
            if (MenuName.equals("Salir del juego")) 
                {
                   System.exit(0);
                } 
            else 
                {
                    String[] str = new String[0];
                    main(str);      
                }
         }
        else
        {
            if(event.getSource() instanceof JButton)
            {
                //GridTextField
                grid.setLayout(new GridLayout(9,9));
                grid.setSize(600,600);
                grid.setLocation(300,30);
                grid.setBackground(Color.black);
                contentPane.add(grid);
                
                if(event.getSource()==basicoButton || event.getSource()==intermedioButton || event.getSource()==avanzadoButton)
                {
                    contentPane.setLayout(null);

                    //RemoveAll
                    basicoButton.setVisible(false);
                    intermedioButton.setVisible(false);
                    avanzadoButton.setVisible(false);
                    background.setVisible(false);

                    //Botones
                    checarButton = new JButton("Checar");
                    checarButton.setBounds(50, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
                    contentPane.add(checarButton);
                    checarButton.addActionListener(this);
                    giveupButton = new JButton("Rendirse");
                    giveupButton.setBounds(50, 200, BUTTON_WIDTH, BUTTON_HEIGHT);
                    contentPane.add(giveupButton);
                    giveupButton.addActionListener(this);
                    inicioButton = new JButton("Inicio");
                    inicioButton.setBounds(50, 300, BUTTON_WIDTH, BUTTON_HEIGHT);
                    contentPane.add(inicioButton);
                    inicioButton.addActionListener(this);
                    
                    //Fondo
                    setLayout(new BorderLayout());
                    background=new JLabel(new ImageIcon("images/panda2.jpg"));
                    add(background);
                    background.setLayout(new FlowLayout());

                    if (event.getSource()==basicoButton) 
                    {
                        setTitle("Nivel Básico");
                        nivel= "Basico";   
                    }       
                    else
                    {
                        if(event.getSource()==intermedioButton)
                        {
                            setTitle("Nivel Intermedio");
                            nivel= "Intermedio";
                        }
                        if(event.getSource()==avanzadoButton)
                        {
                            setTitle("Nivel Avanzado");
                            nivel= "Avanzado";
                        }
                    }
                
                
                //Se selecciona aleatoriamente el sudoku
                n= (int)(Math.random()*5 + 1);

                //Se crea arreglo con archivo
                File archivo;
                FileReader fr = null;
                BufferedReader br;
                archivo = new File ("Niveles/"+nivel+"/"+n+nivel+".txt");
                try 
                {
                     fr = new FileReader (archivo);
                } 
                catch (FileNotFoundException ex) 
                {
                     Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
                }
                br = new BufferedReader(fr);
                for(j=0;j<81;j++)
                {
                     try 
                     {
                         snumeros[j] = br.readLine();
                         numeros[j] = Integer.parseInt(snumeros[j]);
                     } 
                     catch (IOException ex) 
                     {
                         Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
                     }
                }

                //Se crea arreglo con solución
                archivo = new File ("Niveles/"+nivel+"/"+n+nivel+"Solucion.txt");
                try 
                {
                    fr = new FileReader (archivo);
                } 
                catch (FileNotFoundException ex) 
                {
                     Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
                }
                br = new BufferedReader(fr);
                for(g=0;g<81;g++)
                {
                     try 
                     {
                         ssolucion[g] = br.readLine();
                     } 
                     catch (IOException ex) 
                     {
                         Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
                     }
                }

                //Se crea Cuadrícula
                for(i=0;i<81;i++)
                {
                     k = new JTextField();
                     textos[i]= k;
                     k.addActionListener(this);
                     //Se crean Bordes
                     if((i+1)%3==0)
                     {
                         if((i+1)>=19&&(i+1)<=27||(i+1)>=46&&(i+1)<=54)
                         {
                             border = BorderFactory.createMatteBorder(1, 1, 7, 7, Color.black);
                         }
                         else
                         {
                             border = BorderFactory.createMatteBorder(1, 1, 1, 7, Color.black);
                         }                  
                     }
                     else
                     {
                         if((i+1)>=19&&(i+1)<=27||(i+1)>=46&&(i+1)<=54)
                         {
                                border = BorderFactory.createMatteBorder(1, 1, 7, 1, Color.black);       
                         }
                         else
                         {
                            border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black); 
                         }
                     }
                     if(numeros[i]==0)
                     {
                         k.setText("");
                         k.setEditable(true);
                         k.setBorder(border);
                         k.setFont(font2);
                         k.setForeground(Color.gray);
                         k.setHorizontalAlignment(JTextField.CENTER);
                         grid.add(k);   
                      }
                      else
                      {
                         k.setText(""+numeros[i]);
                         k.setEditable(false);
                         k.setBorder(border);
                         k.setFont(font1);
                         k.setHorizontalAlignment(JTextField.CENTER);
                         grid.add(k);    
                      } 
               }
            }
            //Botón checar
            else if(event.getSource()==checarButton)
            {
                try{
                int contador=0;
                for(i=0;i<81;i++)
                {
                    k = textos[i];
                    susuario[i]=k.getText();
                }
                 for(l=0;l<81;l++)
                 {
                     //Se crean Bordes
                     if((i+1)%3==0)
                     {
                         if((i+1)>=19&&(i+1)<=27||(i+1)>=46&&(i+1)<=54)
                         {
                             border = BorderFactory.createMatteBorder(1, 1, 7, 7, Color.black);
                         }
                         else
                         {
                             border = BorderFactory.createMatteBorder(1, 1, 1, 7, Color.black);
                         }                  
                     }
                     else
                     {
                         if((i+1)>=19&&(i+1)<=27||(i+1)>=46&&(i+1)<=54)
                         {
                                border = BorderFactory.createMatteBorder(1, 1, 7, 1, Color.black);       
                         }
                         else
                         {
                            border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black); 
                         }
                     }
                     k = textos[l];
                     if((susuario[l]).equals(""))
                     {
                         
                         k.setEditable(true);
                         
                     }
                     else
                     {   if(susuario[l].equals(ssolucion[l]))
                         {
                             contador=contador+1;
                             k.setEditable(false);
                             k.setForeground(Color.black);
                             k.setFont(font1);
                         }
                         else
                         {
                             k.setText(susuario[l]);
                             k.setEditable(true);
                             k.setBorder(border);
                             k.setFont(font2);
                             k.setForeground(Color.red);
                             k.setHorizontalAlignment(JTextField.CENTER);
                             grid.add(k);  
                         }

                     }
                 }
                 if(contador==81)
                 {
                     JOptionPane.showMessageDialog(null, "¡FELICIDADES HAS GANADO!");
                 }
                }
                catch(NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null, "Número no válido");
                }
                
               //Errores del Usuario
               for(i=0;i<81;i++)
               {
                   //Si es un caracter y no empieza con dígito, si el tamaño es diferente a 1 y no es 0, si es 0
                   if(susuario[i].matches(".")&&susuario[i].matches("[^0-9].*")||!(susuario[i].length() == 1)&&!(susuario[i].length() == 0)||susuario[i].equals("0"))
                   {
                       JOptionPane.showMessageDialog(null, "Número no válido");
                   }
               }
             }
                
            //Rendirse
            else if(event.getSource()==giveupButton)
            {
                int ax = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres rendirte?");
                if(ax == JOptionPane.YES_OPTION)
                {
                    for(i=0;i<81;i++)
                    {
                        k = textos[i];

                    }
                     for(l=0;l<81;l++)
                     {
                         k = textos[l];
                         k.setText(ssolucion[l]);
                         k.setEditable(false);
                         k.setFont(font1);
                         k.setForeground(Color.blue);
                         grid.add(k);
                     }  
                }     
             }
             
            //Inicio Button
            else if(event.getSource()==inicioButton)
            {
                 String[] str = new String[0];
                 main(str);   
            }          
            }
        else 
        {
            for(i=0;i<81;i++)
            {
                k = textos[i];
                susuario[i]=k.getText();
            }
        }
      }
     }
     
}


 










