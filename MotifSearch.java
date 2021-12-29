import java.util.Random;
import java.util.Scanner;

class MotifSearch
{
    //4 molecules
    final static char protein[] = {'A','C','G','T'};
    final static int len = 20;
    final static int size = 600;

    static int subs = 0;
    static int indel = 0;

    public static int minedit(String abc,String xyz)            //perfect
    {
        int l1 = abc.length();
        int l2 = xyz.length();
        int mindis[][] = new int[l1+1][l2+1];
        for(int i=0;i<=l1;i++)
        {
            for(int j=0;j<=l2;j++)
            {
                if(i == 0)
                {
                    mindis[i][j] = j;
                }
                else if(j == 0)
                {
                    mindis[i][j] = i;
                }
                else
                {
                    int mini = Math.min(mindis[i-1][j],mindis[i][j-1]);
                    if(abc.charAt(i-1) == xyz.charAt(j-1))
                    {
                        mini = Math.min(mini+indel,mindis[i-1][j-1]);
                    }
                    else
                    {
                        mini = Math.min(mini+indel,mindis[i-1][j-1]+subs);
                    }
                    mindis[i][j] = mini;
                }
            }
        }
        return mindis[l1][l2];
    }

    public static void main(String[] args)
    {
        Random rand = new Random();
        String strand[] = new String[len];  //creating len number of strand


        for(int i = 0;i<len;i++)
        {
            strand[i] = "";
            for(int j=0;j<size;j++)
            {
                strand[i] += protein[rand.nextInt(4)];
            }
            System.out.println(strand[i]);
        }
        Scanner sc = new Scanner(System.in);
        int L,D;
        System.out.println("Insert and Delete");
        indel = sc.nextInt();
        System.out.println("Substitute");
        subs = sc.nextInt();
        System.out.println(minedit("THIPAK","THIPAK"));

        System.out.println("Length of Motif (L)");
        L = sc.nextInt();

        System.out.println("Maximum edit distance (D)");
        D = sc.nextInt();

        for(int i=0;i<len;i++)                      //len number of strands
        {
            System.out.println("--------------------------------------------------");
            System.out.println("Strand - "+ i);
            System.out.println("--------------------------------------------------");
            for (int j = 0; j <= size-L; j++)        //size - L number of possible motifs
            {
                String motif = strand[i].substring(j,j+L);  //string for motif search
                int count = 0;
                for (int x = 0 ; x < len; x++)      //search in len number of strands
                {
                    if(x == i)                      //skip if same strand
                    {
                        continue;
                    }
                    int maxsize = size - L + (D/indel);
                    boolean flag = false;
                    for (int y = 0; y < maxsize && !flag; y++)   //search in each strand
                    {
//                        if(flag == true)
//                        {
//                            break;
//                        }
                        int lower = L - (D/indel);
                        int upper = L + (D/indel);
                        for (int k = lower; k < upper; k++)     //search for possible string sizes
                        {
                            if (y + k >= size)
                                continue;
                            String sample = "";
                            try
                            {
                                sample = strand[x].substring(y, y + k);
                            }
                            catch (Exception e)
                            {
                                continue;
                            }
                            int dist = minedit(motif,sample);
//                            System.out.println("################");
//                            System.out.println(sample );
//                            System.out.println(dist);
                            if(dist <= D)
                            {
//                                if(count == 0)
//                                {
//                                    System.out.println("MOFTIS OF THE PATTERN '"+ motif + "' ARE:");
//                                    System.out.println(/*count+ 1*/ x +")" + sample);
//                                    System.out.println("   Edit Distance = "+dist);
//                                }
//                                else
//                                {
//                                    System.out.println(/*count+ 1*/ x + ")" + sample);
//                                    System.out.println("   Edit Distance = " + dist);
//                                }
                                count++;
                                flag = true;
                                break;
                            }
                        }
                    }

                }
                if(count >= 19)
                {
                    System.out.println(motif /*+ "  -> Watha <- " + count*/);
                }

            }
        }

    }
}