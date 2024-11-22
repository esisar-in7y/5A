# TP Recherche d’Information – Manipulation d’une Collection de Test

[comment]: <> (http://www.iro.umontreal.ca/~nie/IFT6255/TP1-2006.html)

## 1. Introduction
Ce TP utilise le langage Perl pour manipuler des chaînes de caractères et traiter des données textuelles. Il comprend la création de scripts pour le traitement d’une collection de test, l'indexation et l'accès aux documents.

### Objectifs :
- Manipuler la collection de test
- Écrire des scripts pour nettoyer, analyser et interroger la collection
- Créer des index inversés et un système de recherche

### Étapes à Suivre

##### Nettoyage de la Collection
1. **Suppression de la ponctuation et des accents**
2. **Lemmatisation ou radicalisation**
3. **Création d’un sac de termes**
4. **Utilisation d’une stop list (mots vides)**

##### Analyse de la Collection
- **Calcul de la fréquence des termes**
- **Observation des lois de Zipf et Heaps**
- **Calcul du terme le plus fréquent**

##### Système de Recherche d'Informations
1. **Indexation des documents**
2. **Création des fichiers inverses**
3. **Implémentation des modèles de calcul**
4. **Interrogation de la collection**

## 2. Manipulation des Scripts Perl

### 3.1 Familiarisation avec Perl
- Utiliser l'exemple fourni pour analyser un numéro de téléphone et se familiariser avec la syntaxe Perl.
- Extension des fichiers Perl : `.pl`
- Exécution : `perl monProgramme.pl`

### 3.2 Documentation du Code
- Modifier l’entête de chaque script pour indiquer :
 - .I  indique qu’on commence un nouveau document
 - .T introduit le champ « titre »
 - .W introduit le champ « résumé »
 - .B introduit le numéro où l’article est publié
 - .A introduit les auteurs
 - .N identifie quand ce document a été ajouté dans la collection
 - .X identifie les références

### 4.1 Qu'est-ce qu'une collection de test en Recherche d'Information ?

Une collection de test en Recherche d'Information (RI) est un ensemble de documents structurés, souvent utilisés comme référence pour évaluer et comparer les algorithmes de recherche et de traitement de texte. Ces collections sont généralement accompagnées de requêtes prédéfinies et de résultats attendus, permettant ainsi d'évaluer la précision et l'efficacité des systèmes de RI.

### Exemple concret avec la collection CACM :
La collection CACM (Communications of the ACM) est composée de documents issus de la revue scientifique "Communications of the ACM". Les fichiers de cette collection sont structurés avec des balises spécifiques qui identifient les différentes parties du texte :
- `.I` : ID du document
- `.T` : Titre du document
- `.A` : Auteur du document
- `.W` : Résumé du document

Chaque fichier de cette collection représente un article, avec les informations encapsulées dans ces balises. Cette structuration permet de tester les systèmes de recherche d'information, en vérifiant leur capacité à extraire les informations pertinentes à partir des documents. Si le temps le permet, une deuxième collection pourrait être utilisée pour éprouver les scripts développés, ce qui permettrait de comparer les résultats et de tester la robustesse des algorithmes dans différents contextes documentaires.

---

## 2. Impact de la langue sur la recherche d'information

### Langue utilisée dans la collection CACM :
La collection CACM est entièrement rédigée en anglais. Cela facilite certains processus de traitement du langage comme :
- **La lemmatisation** (réduction des mots à leur forme canonique),
- **Le stemming** (réduction des mots à leur racine),
- **La suppression des mots vides** (stopwords).

Des ressources bien établies existent pour traiter le texte en anglais, comme des listes de stopwords et des algorithmes de lemmatisation efficaces.

### Problème d'une collection multilingue :
Si la collection était multilingue, cela poserait plusieurs défis :
1. **Lemmatisation et stemming** : Chaque langue a ses propres règles grammaticales et linguistiques. Il serait nécessaire d'adapter les algorithmes pour chaque langue, nécessitant des ressources supplémentaires comme des dictionnaires ou des bibliothèques spécifiques pour chaque langue.
   
2. **Stopwords** : Les mots vides varient d’une langue à l’autre. Dans une collection multilingue, plusieurs listes de stopwords seraient nécessaires, rendant le traitement plus complexe.
   
3. **Sémantique** : La signification des mots peut varier entre les langues. Il serait nécessaire de gérer les ambiguïtés linguistiques et les différentes structures syntaxiques, ce qui pourrait réduire la précision des résultats.

Ces défis exigeraient des techniques supplémentaires comme la détection automatique de la langue et des algorithmes de traduction pour unifier les traitements et garantir une analyse précise dans un contexte multilingue.


``` perl= 
# Decode.pl
# Auteur: Bard
# Objectif: Créer un fichier par document à partir du fichier cacm.all

open(F,"cacm.all") || die "Erreur d'ouverture du fichier cacm.all\n";
my $str="";
my $Num=0;
my $Path="Collection";

open(COL,">$Path/Collection") || die "Erreur de creation de Collection\n";
while(!eof(F)){
    if($str =~m/\.I\s(\d+)/){ # Capture le numéro du document
        close(NF) if defined $NF; # Ferme le fichier précédent s'il existe
        $Num=$1; # Utilise la capture du numéro
        print COL "CACM-$Num\n";
        print "Processing ... CACM-$Num\n";
        open(NF,">$Path/CACM-$Num");
    }
    if(($str=~ m/\.T/) || ($str=~ m/\.A/) || ($str=~ m/\.W/) || ($str=~ m/\.B/)) {
        while(($str=<F>)){ # Lecture ligne par ligne dans la boucle
            chomp $str; # Supprime le saut de ligne
            if(($str eq "\.W") || ($str eq "\.B") || ($str eq "\.N") || ($str eq "\.A") || ($str eq "\.X") || ($str eq "\.K") || ($str eq "\.T") || ($str eq "\.I")){
                last; # Sort de la boucle interne si nouvelle balise
            }
            else{
                print NF "$str ";
            }
        }
    }
    else{
        $str=<F>; # Lecture de la ligne suivante
        chomp $str;
    }
}
close(F);
```



``` perl=
#!/usr/bin/perl
# Clean.pl
# Auteur : 
# Ce script enlève les caractères spéciaux et nettoie les fichiers texte

use strict;
use warnings;

my $directory = "Collection";  # Dossier contenant les fichiers à nettoyer
opendir(DIR, $directory) or die "Impossible d'ouvrir $directory : $!";

while (my $file = readdir(DIR)) {
    next if ($file =~ /^\./);  # Ignorer les fichiers cachés
    clean_file("$directory/$file");
}

closedir(DIR);

# Fonction pour nettoyer le fichier
sub clean_file {
    my ($file) = @_;
    open(my $in, '<', $file) or die "Impossible d'ouvrir $file : $!";
    my $cleaned_content = "";

    while (my $line = <$in>) {
        if ($line =~ /^\.[A-Z]/) {
            if ($line =~ /^\.I/) {

            }else{
                $cleaned_content .= "\n";
            }
            $cleaned_content .= substr($line, 0, 2);
            $cleaned_content .= " ";
            $line = substr($line, 3);
        }
        # Supprimer la ponctuation et les accents
        $line =~ s/^\s+//;      # Remove leading whitespace
        $line =~ s/\s+$//;      # Remove trailing whitespace
        $line =~ s/([^\w\s]|[\n\r])//g;
        while($line =~ s/([\s]{2,}|[\t])/ /g){
            last if !defined $&;
        };
        $cleaned_content .= $line;
    }

    close($in);
    
    # Sauvegarder le fichier nettoyé
    open(my $out, '>', $file) or die "Impossible de sauvegarder $file : $!";
    print $out $cleaned_content;
    close($out);

    print "$file a été nettoyé.\n";
}
```
 
``` perl=
#!/usr/bin/perl
# Remove.pl
# Auteur : [Votre nom]
# Ce script supprime les mots vides des fichiers de la collection

use strict;
use warnings;

# Charger la liste des mots vides (stopwords)
my %stopwords;
open(my $sw, '<', 'commonwords') or die "Impossible d'ouvrir le fichier des mots vides : $!";
while (my $word = <$sw>) {
    chomp $word;
    $stopwords{lc($word)} = 1;
}
close($sw);

# Parcourir les fichiers de la collection
my $directory = "collection";
opendir(DIR, $directory) or die "Impossible d'ouvrir $directory : $!";

while (my $file = readdir(DIR)) {
    next if ($file =~ /^\./);  # Ignorer les fichiers cachés
    remove_stopwords("$directory/$file");
}

closedir(DIR);

# Fonction pour supprimer les mots vides
sub remove_stopwords {
    my ($file) = @_;
    open(my $in, '<', $file) or die "Impossible d'ouvrir $file : $!";
    my $filtered_content = "";

    while (my $line = <$in>) {
        my @words = split(/\s+/, $line);
        foreach my $word (@words) {
            # Ne garder que les mots qui ne sont pas dans la stoplist
            unless (exists $stopwords{lc($word)}) {
                $filtered_content .= "$word ";
            }
        }
    }

    close($in);
    
    # Sauvegarder le fichier filtré
    open(my $out, '>', $file) or die "Impossible de sauvegarder $file : $!";
    print $out $filtered_content;
    close($out);

    print "$file a été filtré.\n";
}
```

# DOC
```python=
`import os
import glob
from collections import Counter
import matplotlib.pyplot as plt

# Step 1: Collect word counts
folder_path = 'Collection'
word_counts = Counter()

for filename in glob.glob(os.path.join(folder_path, '*')):
    if os.path.isfile(filename) and filename.endswith(".stp"):
        try:
            with open(filename, 'r') as file:
                for word in file.read().lower().split():
                    word_counts[word] += 1
        except Exception as e:
            print(f"Error processing {filename}: {str(e)}")

# Step 2: Prepare data for plotting
most_common_words = word_counts.most_common(50)  # Get top 50 words
words, counts = zip(*most_common_words)  # Unzip into two lists

# Step 3: Create the plot
plt.figure(figsize=(12, 6))
plt.bar(range(len(counts)), counts, tick_label=words)
plt.xticks(rotation=90)
plt.xlabel('Words')
plt.ylabel('Frequency')
plt.title('Top 50 Words Frequency')
plt.grid(axis='y')

# Step 4: Show the plot
plt.tight_layout()
plt.show()

# Calculate and print frequency of the most common word
most_common_word, most_common_word_count = most_common_words[0]
words,counts=zip(*word_counts.most_common())
total_count = sum(counts)
print(f"Frequency of '{most_common_word}': {most_common_word_count / total_count * 100:.2f}%")

with open("vocab","w") as writer:
    writer.write("\n".join(words))

for filename in glob.glob(os.path.join(folder_path, '*')):
    if os.path.isfile(filename) and filename.endswith(".stp"):
        try:
            with open(filename, 'r') as file:
                word_counts = Counter()
                for word in file.read().lower().split():
                    word_counts[word] += 1
                
                with open(f"{filename}_df","w") as writer:
                    for word, df in word_counts.items():
                        writer.write(f"{word} {df}\n")
    
        except Exception as e:
            print(f"Error processing {filename}: {str(e)}")

```
