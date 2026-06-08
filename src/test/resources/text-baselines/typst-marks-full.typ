#set page(
  paper: "us-letter",
  margin: (top: 1in, bottom: 0.75in, x: 0.75in),
  columns: 2,
  footer: align(center)[Joshua, Judges, and Ruth — March 28, 2026],
)
#set text(font: "Libertinus Serif", size: 10pt)
#set par(justify: true)
#show footnote.entry: set text(size: 9pt)

// Built-in highlight colors (matching DOCX)
#let namesColor  = rgb(204, 204, 204)
#let numsColor   = rgb(255, 182, 108)
#let divineColor = rgb(255, 255, 0)
#let menColor = rgb(153, 204, 255)
#let placesColor = rgb(153, 255, 153)
#let womenColor = rgb(255, 153, 255)
#let myhl(color, body) = highlight(fill: color, body)
#let myname(body)   = myhl(namesColor,  body)
#let mynumber(body) = myhl(numsColor,   body)
#let versenum(n) = box(
    fill: rgb("404040"),
    inset: (x: 3pt, y: 1pt),
    radius: 1pt,
    text(fill: white, weight: "bold", font: "Libertinus Serif")[#n],
)
#let chapter-heading(label) = heading(
    level: 1, outlined: false,
    text(font: "Libertinus Serif", size: 14pt, weight: "bold")[#label],
)
#let section-heading(label) = heading(
    level: 2, outlined: false,
    text(font: "Libertinus Serif", size: 12pt, weight: "bold")[#label],
)
#let vin = h(2em)

  
#chapter-heading[Joshua 1]


#section-heading[God Commissions Joshua]


#versenum(1) After the death of #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]], the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua] the son of #myhl(menColor)[Nun], #myhl(menColor)[Moses]’ #underline[assistant], 
#versenum(2) “#myhl(menColor)[Moses] my servant is dead. Now therefore arise, go over this #myhl(placesColor)[Jordan], you and all this people, into the land that I am giving to them, to the people of #myname[Israel]. 
#versenum(3) Every place that the #underline[sole] of your foot will #underline[tread] upon I have given to you, just as I promised to #myhl(menColor)[Moses]. 
#versenum(4) From the wilderness and this #myhl(placesColor)[Lebanon] as far as the great river, the river #myhl(placesColor)[Euphrates], all the land of the #myname[Hittites] to the #myhl(placesColor)[Great Sea] toward the going down of the sun shall be your territory. 
#versenum(5) No man shall be able to stand before you all the days of your life. Just as I was with #myhl(menColor)[Moses], so I will be with you. I will not leave you or forsake you. 
#versenum(6) Be strong and courageous, for you shall cause this people to inherit the land that I swore to their fathers to give them. 
#versenum(7) Only be strong and very courageous, being careful to do according to all the law that #myhl(menColor)[Moses] my servant commanded you. Do not turn from it to the right hand or to the left, that you may have good success#footnote[Joshua 1:7 Or #emph[may act wisely]] wherever you go. 
#versenum(8) This Book of the Law shall not depart from your mouth, but you shall #underline[meditate] on it day and night, so that you may be careful to do according to all that is written in it. For then you will make your way #underline[prosperous], and then you will have good success. 
#versenum(9) Have I not commanded you? Be strong and courageous. Do not be #underline[frightened], and do not be dismayed, for the #myhl(divineColor)[#smallcaps[Lord] your God] is with you wherever you go.”


  
#section-heading[Joshua Assumes Command]


#versenum(10) And #myhl(menColor)[Joshua] commanded the officers of the people, 
#versenum(11) “Pass through the midst of the camp and command the people, ‘Prepare your provisions, for within #mynumber[three] days you are to pass over this #myhl(placesColor)[Jordan] to go in to take possession of the land that the #myhl(divineColor)[#smallcaps[Lord] your God] is giving you to possess.’”


  
#versenum(12) And to the #myname[Reubenites], the #myname[Gadites], and the #mynumber[half]-tribe of #myname[Manasseh] #myhl(menColor)[Joshua] said, 
#versenum(13) “Remember the word that #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] commanded you, saying, ‘The #myhl(divineColor)[#smallcaps[Lord] your God] is #underline[providing] you a place of rest and will give you this land.’ 
#versenum(14) Your wives, your little ones, and your livestock shall remain in the land that #myhl(menColor)[Moses] gave you beyond the #myhl(placesColor)[Jordan], but all the men of valor among you shall pass over armed before your brothers and shall help them, 
#versenum(15) until the #myhl(divineColor)[#smallcaps[Lord]] gives rest to your brothers as he has to you, and they also take possession of the land that the #myhl(divineColor)[#smallcaps[Lord] your God] is giving them. Then you shall return to the land of your possession and shall possess it, the land that #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] gave you beyond the #myhl(placesColor)[Jordan] toward the sunrise.”


  
#versenum(16) And they answered #myhl(menColor)[Joshua], “All that you have commanded us we will do, and wherever you send us we will go. 
#versenum(17) Just as we obeyed #myhl(menColor)[Moses] in all things, so we will obey you. Only may the #myhl(divineColor)[#smallcaps[Lord] your God] be with you, as he was with #myhl(menColor)[Moses]! 
#versenum(18) Whoever rebels against your commandment and #underline[disobeys] your words, whatever you command him, shall be put to death. Only be strong and courageous.”


  
#chapter-heading[Joshua 2]


#section-heading[Rahab Hides the Spies]


#versenum(1) And #myhl(menColor)[Joshua] the son of #myhl(menColor)[Nun] sent#footnote[Joshua 2:1 Or #emph[had sent]] #mynumber[two] men secretly from #myhl(placesColor)[Shittim] as spies, saying, “Go, view the land, #underline[especially] #myhl(placesColor)[Jericho].” And they went and came into the house of a prostitute whose name was #myhl(womenColor)[Rahab] and lodged there. 
#versenum(2) And it was told to the king of #myhl(placesColor)[Jericho], “Behold, men of #myname[Israel] have come here tonight to search out the land.” 
#versenum(3) Then the king of #myhl(placesColor)[Jericho] sent to #myhl(womenColor)[Rahab], saying, “Bring out the men who have come to you, who entered your house, for they have come to search out all the land.” 
#versenum(4) But the woman had taken the #mynumber[two] men and hidden them. And she said, “True, the men came to me, but I did not know where they were from. 
#versenum(5) And when the gate was about to be closed at #underline[dark], the men went out. I do not know where the men went. Pursue them quickly, for you will #underline[overtake] them.” 
#versenum(6) But she had brought them up to the roof and hid them with the #underline[stalks] of flax that she had laid in order on the roof. 
#versenum(7) So the men pursued after them on the way to the #myhl(placesColor)[Jordan] as far as the fords. And the gate was shut as soon as the pursuers had gone out.


  
#versenum(8) Before the men#footnote[Joshua 2:8 Hebrew #emph[they]] lay down, she came up to them on the roof 
#versenum(9) and said to the men, “I know that the #myhl(divineColor)[#smallcaps[Lord]] has given you the land, and that the fear of you has fallen upon us, and that all the inhabitants of the land melt away before you. 
#versenum(10) For we have heard how the #myhl(divineColor)[#smallcaps[Lord]] dried up the water of the #myhl(placesColor)[Red Sea] before you when you came out of #myhl(placesColor)[Egypt], and what you did to the #mynumber[two] kings of the #myname[Amorites] who were beyond the #myhl(placesColor)[Jordan], to #myhl(menColor)[Sihon] and #myhl(menColor)[Og], whom you devoted to destruction.#footnote[Joshua 2:10 That is, set apart (devoted) as an offering to the Lord (for destruction)] 
#versenum(11) And as soon as we heard it, our hearts melted, and there was no spirit left in any man because of you, for the #myhl(divineColor)[#smallcaps[Lord] your God], he is #myhl(divineColor)[God] in the heavens above and on the earth #underline[beneath]. 
#versenum(12) Now then, please swear to me by the #myhl(divineColor)[#smallcaps[Lord]] that, as I have dealt kindly with you, you also will deal kindly with my father’s house, and give me a #underline[sure] sign 
#versenum(13) that you will save alive my father and mother, my brothers and #underline[sisters], and all who belong to them, and deliver our lives from death.” 
#versenum(14) And the men said to her, “Our life for yours even to death! If you do not tell this business of ours, then when the #myhl(divineColor)[#smallcaps[Lord]] gives us the land we will deal kindly and #underline[faithfully] with you.”


  
#versenum(15) Then she let them down by a #underline[rope] through the window, for her house was built into the city wall, so that she lived in the wall. 
#versenum(16) And she said#footnote[Joshua 2:16 Or #emph[had said]] to them, “Go into the hills, or the pursuers will #underline[encounter] you, and hide there #mynumber[three] days until the pursuers have returned. Then afterward you may go your way.” 
#versenum(17) The men said to her, “We will be guiltless with respect to this oath of yours that you have made us swear. 
#versenum(18) Behold, when we come into the land, you shall #underline[tie] this scarlet cord in the window through which you let us down, and you shall gather into your house your father and mother, your brothers, and all your father’s household. 
#versenum(19) Then if anyone goes out of the doors of your house into the #underline[street], his blood shall be on his own head, and we shall be guiltless. But if a hand is laid on anyone who is with you in the house, his blood shall be on our head. 
#versenum(20) But if you tell this business of ours, then we shall be guiltless with respect to your oath that you have made us swear.” 
#versenum(21) And she said, “According to your words, so be it.” Then she sent them away, and they departed. And she #underline[tied] the scarlet cord in the window.


  
#versenum(22) They departed and went into the hills and remained there #mynumber[three] days until the pursuers returned, and the pursuers searched all along the way and found nothing. 
#versenum(23) Then the #mynumber[two] men returned. They came down from the hills and passed over and came to #myhl(menColor)[Joshua] the son of #myhl(menColor)[Nun], and they told him all that had happened to them. 
#versenum(24) And they said to #myhl(menColor)[Joshua], “Truly the #myhl(divineColor)[#smallcaps[Lord]] has given all the land into our hands. And also, all the inhabitants of the land melt away because of us.”


  
#chapter-heading[Joshua 3]


#section-heading[Israel Crosses the Jordan]


#versenum(1) Then #myhl(menColor)[Joshua] rose early in the morning and they set out from #myhl(placesColor)[Shittim]. And they came to the #myhl(placesColor)[Jordan], he and all the people of #myname[Israel], and lodged there before they passed over. 
#versenum(2) At the end of #mynumber[three] days the officers went through the camp 
#versenum(3) and commanded the people, “As soon as you see the ark of the covenant of the #myhl(divineColor)[#smallcaps[Lord] your God] being carried by the Levitical priests, then you shall set out from your place and follow it. 
#versenum(4) Yet there shall be a distance between you and it, about #mynumber[2,000] #underline[cubits]#footnote[Joshua 3:4 A #emph[cubit] was about 18 inches or 45 centimeters] in length. Do not come near it, in order that you may know the way you shall go, for you have not passed this way before.” 
#versenum(5) Then #myhl(menColor)[Joshua] said to the people, “Consecrate yourselves, for tomorrow the #myhl(divineColor)[#smallcaps[Lord]] will do wonders among you.” 
#versenum(6) And #myhl(menColor)[Joshua] said to the priests, “Take up the ark of the covenant and pass on before the people.” So they took up the ark of the covenant and went before the people.


  
#versenum(7) The #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], “Today I will begin to #underline[exalt] you in the sight of all #myname[Israel], that they may know that, as I was with #myhl(menColor)[Moses], so I will be with you. 
#versenum(8) And as for you, command the priests who bear the ark of the covenant, ‘When you come to the brink of the waters of the #myhl(placesColor)[Jordan], you shall stand still in the #myhl(placesColor)[Jordan].’” 
#versenum(9) And #myhl(menColor)[Joshua] said to the people of #myname[Israel], “Come here and listen to the words of the #myhl(divineColor)[#smallcaps[Lord] your God].” 
#versenum(10) And #myhl(menColor)[Joshua] said, “Here is how you shall know that the living #myhl(divineColor)[God] is among you and that he will without #underline[fail] drive out from before you the #myname[Canaanites], the #myname[Hittites], the #myname[Hivites], the #myname[Perizzites], the #myname[Girgashites], the #myname[Amorites], and the #myname[Jebusites]. 
#versenum(11) Behold, the ark of the covenant of the #myhl(divineColor)[Lord] of all the earth#footnote[Joshua 3:11 Hebrew #emph[the ark of the covenant, the Lord of all the earth]] is passing over before you into the #myhl(placesColor)[Jordan]. 
#versenum(12) Now therefore take #mynumber[twelve] men from the tribes of #myname[Israel], from each tribe a man. 
#versenum(13) And when the soles of the feet of the priests bearing the ark of the #myhl(divineColor)[#smallcaps[Lord]], the #myhl(divineColor)[Lord] of all the earth, shall rest in the waters of the #myhl(placesColor)[Jordan], the waters of the #myhl(placesColor)[Jordan] shall be cut off from flowing, and the waters coming down from above shall stand in #mynumber[one] heap.”


  
#versenum(14) So when the people set out from their tents to pass over the #myhl(placesColor)[Jordan] with the priests bearing the ark of the covenant before the people, 
#versenum(15) and as soon as those bearing the ark had come as far as the #myhl(placesColor)[Jordan], and the feet of the priests bearing the ark were #underline[dipped] in the brink of the water (now the #myhl(placesColor)[Jordan] #underline[overflows] all its banks throughout the time of harvest), 
#versenum(16) the waters coming down from above stood and rose up in a heap very far away, at #underline[#myhl(placesColor)[Adam]], the city that is beside #underline[#myhl(placesColor)[Zarethan]], and those flowing down toward the #myhl(placesColor)[Sea of the Arabah], the #myhl(placesColor)[Salt Sea], were completely cut off. And the people passed over opposite #myhl(placesColor)[Jericho]. 
#versenum(17) Now the priests bearing the ark of the covenant of the #myhl(divineColor)[#smallcaps[Lord]] stood firmly on dry ground in the midst of the #myhl(placesColor)[Jordan], and all #myname[Israel] was passing over on dry ground until all the nation finished passing over the #myhl(placesColor)[Jordan].


  
#chapter-heading[Joshua 4]


#section-heading[Twelve Memorial Stones from the Jordan]


#versenum(1) When all the nation had finished passing over the #myhl(placesColor)[Jordan], the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], 
#versenum(2) “Take #mynumber[twelve] men from the people, from each tribe a man, 
#versenum(3) and command them, saying, ‘Take #mynumber[twelve] stones from here out of the midst of the #myhl(placesColor)[Jordan], from the very place where the priests’ feet stood firmly, and bring them over with you and lay them down in the place where you lodge tonight.’” 
#versenum(4) Then #myhl(menColor)[Joshua] called the #mynumber[twelve] men from the people of #myname[Israel], whom he had appointed, a man from each tribe. 
#versenum(5) And #myhl(menColor)[Joshua] said to them, “Pass on before the ark of the #myhl(divineColor)[#smallcaps[Lord] your God] into the midst of the #myhl(placesColor)[Jordan], and take up each of you a stone upon his shoulder, according to the number of the tribes of the people of #myname[Israel], 
#versenum(6) that this may be a sign among you. When your children ask in time to come, ‘What do those stones mean to you?’ 
#versenum(7) then you shall tell them that the waters of the #myhl(placesColor)[Jordan] were cut off before the ark of the covenant of the #myhl(divineColor)[#smallcaps[Lord]]. When it passed over the #myhl(placesColor)[Jordan], the waters of the #myhl(placesColor)[Jordan] were cut off. So these stones shall be to the people of #myname[Israel] a #underline[memorial] forever.”


  
#versenum(8) And the people of #myname[Israel] did just as #myhl(menColor)[Joshua] commanded and took up #mynumber[twelve] stones out of the midst of the #myhl(placesColor)[Jordan], according to the number of the tribes of the people of #myname[Israel], just as the #myhl(divineColor)[#smallcaps[Lord]] told #myhl(menColor)[Joshua]. And they carried them over with them to the place where they lodged and laid them down#footnote[Joshua 4:8 Or #emph[to rest]] there. 
#versenum(9) And #myhl(menColor)[Joshua] set up#footnote[Joshua 4:9 Or #emph[Joshua had set up]] #mynumber[twelve] stones in the midst of the #myhl(placesColor)[Jordan], in the place where the feet of the priests bearing the ark of the covenant had stood; and they are there to this day. 
#versenum(10) For the priests bearing the ark stood in the midst of the #myhl(placesColor)[Jordan] until everything was finished that the #myhl(divineColor)[#smallcaps[Lord]] commanded #myhl(menColor)[Joshua] to tell the people, according to all that #myhl(menColor)[Moses] had commanded #myhl(menColor)[Joshua].


  The people passed over in #underline[haste]. 
#versenum(11) And when all the people had finished passing over, the ark of the #myhl(divineColor)[#smallcaps[Lord]] and the priests passed over before the people. 
#versenum(12) The sons of #myname[Reuben] and the sons of #myname[Gad] and the #mynumber[half]-tribe of #myname[Manasseh] passed over armed before the people of #myname[Israel], as #myhl(menColor)[Moses] had told them. 
#versenum(13) About #underline[#mynumber[40,000]] ready for war passed over before the #myhl(divineColor)[#smallcaps[Lord]] for battle, to the plains of #myhl(placesColor)[Jericho]. 
#versenum(14) On that day the #myhl(divineColor)[#smallcaps[Lord]] #underline[exalted] #myhl(menColor)[Joshua] in the sight of all #myname[Israel], and they stood in awe of him just as they had stood in awe of #myhl(menColor)[Moses], all the days of his life.


  
#versenum(15) And the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], 
#versenum(16) “Command the priests bearing the ark of the #underline[testimony] to come up out of the #myhl(placesColor)[Jordan].” 
#versenum(17) So #myhl(menColor)[Joshua] commanded the priests, “Come up out of the #myhl(placesColor)[Jordan].” 
#versenum(18) And when the priests bearing the ark of the covenant of the #myhl(divineColor)[#smallcaps[Lord]] came up from the midst of the #myhl(placesColor)[Jordan], and the soles of the priests’ feet were lifted up on dry ground, the waters of the #myhl(placesColor)[Jordan] returned to their place and #underline[overflowed] all its banks, as before.


  
#versenum(19) The people came up out of the #myhl(placesColor)[Jordan] on the #underline[#mynumber[tenth]] day of the #mynumber[first] month, and they encamped at #myhl(placesColor)[Gilgal] on the east border of #myhl(placesColor)[Jericho]. 
#versenum(20) And those #mynumber[twelve] stones, which they took out of the #myhl(placesColor)[Jordan], #myhl(menColor)[Joshua] set up at #myhl(placesColor)[Gilgal]. 
#versenum(21) And he said to the people of #myname[Israel], “When your children ask their fathers in times to come, ‘What do these stones mean?’ 
#versenum(22) then you shall let your children know, ‘#myname[Israel] passed over this #myhl(placesColor)[Jordan] on dry ground.’ 
#versenum(23) For the #myhl(divineColor)[#smallcaps[Lord] your God] dried up the waters of the #myhl(placesColor)[Jordan] for you until you passed over, as the #myhl(divineColor)[#smallcaps[Lord] your God] did to the #myhl(placesColor)[Red Sea], which he dried up for us until we passed over, 
#versenum(24) so that all the peoples of the earth may know that the hand of the #myhl(divineColor)[#smallcaps[Lord]] is mighty, that you may fear the #myhl(divineColor)[#smallcaps[Lord] your God] forever.”#footnote[Joshua 4:24 Or #emph[all the days]]


  
#chapter-heading[Joshua 5]


#section-heading[The New Generation Circumcised]


#versenum(1) As soon as all the kings of the #myname[Amorites] who were beyond the #myhl(placesColor)[Jordan] to the west, and all the kings of the #myname[Canaanites] who were by the sea, heard that the #myhl(divineColor)[#smallcaps[Lord]] had dried up the waters of the #myhl(placesColor)[Jordan] for the people of #myname[Israel] until they had crossed over, their hearts melted and there was no longer any spirit in them because of the people of #myname[Israel].


  
#versenum(2) At that time the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], “Make flint knives and #underline[circumcise] the sons of #myname[Israel] a #mynumber[second] time.” 
#versenum(3) So #myhl(menColor)[Joshua] made flint knives and circumcised the sons of #myname[Israel] at #underline[#myhl(placesColor)[Gibeath-haaraloth]].#footnote[Joshua 5:3 #emph[Gibeath-haaraloth] means #emph[the hill of the foreskins]] 
#versenum(4) And this is the #underline[reason] why #myhl(menColor)[Joshua] circumcised them: all the #underline[males] of the people who came out of #myhl(placesColor)[Egypt], all the men of war, had died in the wilderness on the way after they had come out of #myhl(placesColor)[Egypt]. 
#versenum(5) Though all the people who came out had been circumcised, yet all the people who were born on the way in the wilderness after they had come out of #myhl(placesColor)[Egypt] had not been circumcised. 
#versenum(6) For the people of #myname[Israel] walked #mynumber[forty] years in the wilderness, until all the nation, the men of war who came out of #myhl(placesColor)[Egypt], #underline[perished], because they did not obey the voice of the #myhl(divineColor)[#smallcaps[Lord]]; the #myhl(divineColor)[#smallcaps[Lord]] swore to them that he would not let them see the land that the #myhl(divineColor)[#smallcaps[Lord]] had sworn to their fathers to give to us, a land flowing with milk and honey. 
#versenum(7) So it was their children, whom he raised up in their place, that #myhl(menColor)[Joshua] circumcised. For they were uncircumcised, because they had not been circumcised on the way.


  
#versenum(8) When the #underline[circumcising] of the whole nation was finished, they remained in their places in the camp until they were #underline[healed]. 
#versenum(9) And the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], “Today I have #underline[rolled] away the reproach of #myhl(placesColor)[Egypt] from you.” And so the name of that place is called #myhl(placesColor)[Gilgal]#footnote[Joshua 5:9 #emph[Gilgal] sounds like the Hebrew for #emph[to roll]] to this day.


  
#section-heading[First Passover in Canaan]


#versenum(10) While the people of #myname[Israel] were encamped at #myhl(placesColor)[Gilgal], they kept the #myname[Passover] on the #underline[#mynumber[fourteenth]] day of the month in the evening on the plains of #myhl(placesColor)[Jericho]. 
#versenum(11) And the day after the #myname[Passover], on that very day, they ate of the produce of the land, unleavened cakes and #underline[parched] grain. 
#versenum(12) And the manna ceased the day after they ate of the produce of the land. And there was no longer manna for the people of #myname[Israel], but they ate of the fruit of the land of #myhl(placesColor)[Canaan] that year.


  
#section-heading[The Commander of the LORD’s Army]


#versenum(13) When #myhl(menColor)[Joshua] was by #myhl(placesColor)[Jericho], he lifted up his eyes and looked, and behold, a man was standing before him with his drawn sword in his hand. And #myhl(menColor)[Joshua] went to him and said to him, “Are you for us, or for our #underline[adversaries]?” 
#versenum(14) And he said, “No; but I am the commander of the army of the #myhl(divineColor)[#smallcaps[Lord]]. Now I have come.” And #myhl(menColor)[Joshua] fell on his face to the earth and worshiped#footnote[Joshua 5:14 Or #emph[and paid homage]] and said to him, “What does my lord say to his servant?” 
#versenum(15) And the commander of the #myhl(divineColor)[#smallcaps[Lord]]’s army said to #myhl(menColor)[Joshua], “Take off your sandals from your feet, for the place where you are standing is holy.” And #myhl(menColor)[Joshua] did so.


  
#chapter-heading[Joshua 6]


#section-heading[The Fall of Jericho]


#versenum(1) Now #myhl(placesColor)[Jericho] was shut up inside and outside because of the people of #myname[Israel]. None went out, and none came in. 
#versenum(2) And the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], “See, I have given #myhl(placesColor)[Jericho] into your hand, with its king and mighty men of valor. 
#versenum(3) You shall march around the city, all the men of war going around the city once. Thus shall you do for #mynumber[six] days. 
#versenum(4) #mynumber[Seven] priests shall bear #mynumber[seven] trumpets of rams’ horns before the ark. On the #mynumber[seventh] day you shall march around the city #mynumber[seven] times, and the priests shall blow the trumpets. 
#versenum(5) And when they make a long #underline[blast] with the #underline[ram’s] #underline[horn], when you hear the sound of the trumpet, then all the people shall shout with a great shout, and the wall of the city will fall down flat,#footnote[Joshua 6:5 Hebrew #emph[under itself]; also verse 20] and the people shall go up, everyone straight before him.” 
#versenum(6) So #myhl(menColor)[Joshua] the son of #myhl(menColor)[Nun] called the priests and said to them, “Take up the ark of the covenant and let #mynumber[seven] priests bear #mynumber[seven] trumpets of rams’ horns before the ark of the #myhl(divineColor)[#smallcaps[Lord]].” 
#versenum(7) And he said to the people, “Go forward. March around the city and let the armed men pass on before the ark of the #myhl(divineColor)[#smallcaps[Lord]].”


  
#versenum(8) And just as #myhl(menColor)[Joshua] had commanded the people, the #mynumber[seven] priests bearing the #mynumber[seven] trumpets of rams’ horns before the #myhl(divineColor)[#smallcaps[Lord]] went forward, blowing the trumpets, with the ark of the covenant of the #myhl(divineColor)[#smallcaps[Lord]] following them. 
#versenum(9) The armed men were walking before the priests who were blowing the trumpets, and the rear guard was walking after the ark, while the trumpets blew continually. 
#versenum(10) But #myhl(menColor)[Joshua] commanded the people, “You shall not shout or make your voice heard, neither shall any word go out of your mouth, until the day I tell you to shout. Then you shall shout.” 
#versenum(11) So he #underline[caused] the ark of the #myhl(divineColor)[#smallcaps[Lord]] to #underline[circle] the city, going about it once. And they came into the camp and spent the night in the camp.


  
#versenum(12) Then #myhl(menColor)[Joshua] rose early in the morning, and the priests took up the ark of the #myhl(divineColor)[#smallcaps[Lord]]. 
#versenum(13) And the #mynumber[seven] priests bearing the #mynumber[seven] trumpets of rams’ horns before the ark of the #myhl(divineColor)[#smallcaps[Lord]] walked on, and they blew the trumpets continually. And the armed men were walking before them, and the rear guard was walking after the ark of the #myhl(divineColor)[#smallcaps[Lord]], while the trumpets blew continually. 
#versenum(14) And the #mynumber[second] day they marched around the city once, and returned into the camp. So they did for #mynumber[six] days.


  
#versenum(15) On the #mynumber[seventh] day they rose early, at the dawn of day, and marched around the city in the same manner #mynumber[seven] times. It was only on that day that they marched around the city #mynumber[seven] times. 
#versenum(16) And at the #mynumber[seventh] time, when the priests had blown the trumpets, #myhl(menColor)[Joshua] said to the people, “Shout, for the #myhl(divineColor)[#smallcaps[Lord]] has given you the city. 
#versenum(17) And the city and all that is within it shall be devoted to the #myhl(divineColor)[#smallcaps[Lord]] for destruction.#footnote[Joshua 6:17 That is, set apart (devoted) as an offering to the Lord (for destruction); also verses 18, 21] Only #myhl(womenColor)[Rahab] the prostitute and all who are with her in her house shall live, because she hid the messengers whom we sent. 
#versenum(18) But you, keep yourselves from the things devoted to destruction, lest when you have devoted them you take any of the devoted things and make #myhl(placesColor)[the camp of Israel] a thing for destruction and bring trouble upon it. 
#versenum(19) But all silver and gold, and every #underline[vessel] of bronze and iron, are holy to the #myhl(divineColor)[#smallcaps[Lord]]; they shall go into the treasury of the #myhl(divineColor)[#smallcaps[Lord]].” 
#versenum(20) So the people shouted, and the trumpets were blown. As soon as the people heard the sound of the trumpet, the people shouted a great shout, and the wall fell down flat, so that the people went up into the city, every man straight before him, and they captured the city. 
#versenum(21) Then they devoted all in the city to destruction, both men and women, young and old, oxen, sheep, and donkeys, with the edge of the sword.


  
#versenum(22) But to the #mynumber[two] men who had spied out the land, #myhl(menColor)[Joshua] said, “Go into the #underline[prostitute’s] house and bring out from there the woman and all who belong to her, as you swore to her.” 
#versenum(23) So the young men who had been spies went in and brought out #myhl(womenColor)[Rahab] and her father and mother and brothers and all who belonged to her. And they brought all her relatives and put them outside #myhl(placesColor)[the camp of Israel]. 
#versenum(24) And they burned the city with fire, and everything in it. Only the silver and gold, and the vessels of bronze and of iron, they put into the treasury of the house of the #myhl(divineColor)[#smallcaps[Lord]]. 
#versenum(25) But #myhl(womenColor)[Rahab] the prostitute and her father’s household and all who belonged to her, #myhl(menColor)[Joshua] saved alive. And she has lived in #myhl(placesColor)[Israel] to this day, because she hid the messengers whom #myhl(menColor)[Joshua] sent to spy out #myhl(placesColor)[Jericho].


  
#versenum(26) #myhl(menColor)[Joshua] laid an oath on them at that time, saying, “Cursed before the #myhl(divineColor)[#smallcaps[Lord]] be the man who rises up and #underline[rebuilds] this city, #myhl(placesColor)[Jericho].


    “At the cost of his firstborn\
    #vin shall he lay its #underline[foundation],\
    and at the cost of his youngest son\
    #vin shall he set up its gates.”\


      
#versenum(27) So the #myhl(divineColor)[#smallcaps[Lord]] was with #myhl(menColor)[Joshua], and his #underline[fame] was in all the land.


  
#chapter-heading[Joshua 7]


#section-heading[Israel Defeated at Ai]


#versenum(1) But the people of #myname[Israel] broke faith in regard to the devoted things, for #myhl(menColor)[Achan] the son of #myhl(menColor)[Carmi], son of #myhl(menColor)[Zabdi], son of #myhl(menColor)[Zerah], of the tribe of #myname[Judah], took some of the devoted things. And the anger of the #myhl(divineColor)[#smallcaps[Lord]] burned against the people of #myname[Israel].


  
#versenum(2) #myhl(menColor)[Joshua] sent men from #myhl(placesColor)[Jericho] to #myhl(placesColor)[Ai], which is near #myhl(placesColor)[Beth-aven], east of #myhl(placesColor)[Bethel], and said to them, “Go up and spy out the land.” And the men went up and spied out #myhl(placesColor)[Ai]. 
#versenum(3) And they returned to #myhl(menColor)[Joshua] and said to him, “Do not have all the people go up, but let about #mynumber[two] or #mynumber[three thousand] men go up and attack #myhl(placesColor)[Ai]. Do not make the whole people #underline[toil] up there, for they are #underline[few].” 
#versenum(4) So about #mynumber[three thousand] men went up there from the people. And they fled before the men of #myhl(placesColor)[Ai], 
#versenum(5) and the men of #myhl(placesColor)[Ai] killed about #underline[#mynumber[thirty-six]] of their men and chased them before the gate as far as #underline[#myhl(placesColor)[Shebarim]] and struck them at the #underline[descent]. And the hearts of the people melted and became as water.


  
#versenum(6) Then #myhl(menColor)[Joshua] tore his clothes and fell to the earth on his face before the ark of the #myhl(divineColor)[#smallcaps[Lord]] until the evening, he and the elders of #myname[Israel]. And they put #underline[dust] on their heads. 
#versenum(7) And #myhl(menColor)[Joshua] said, “Alas, O #myhl(divineColor)[Lord] #myhl(divineColor)[GOD], why have you brought this people over the #myhl(placesColor)[Jordan] at all, to give us into the hands of the #myname[Amorites], to destroy us? Would that we had been content to dwell beyond the #myhl(placesColor)[Jordan]! 
#versenum(8) O #myhl(divineColor)[Lord], what can I say, when #myname[Israel] has turned their backs before their enemies! 
#versenum(9) For the #myname[Canaanites] and all the inhabitants of the land will hear of it and will #underline[surround] us and cut off our name from the earth. And what will you do for your great name?”


  
#section-heading[The Sin of Achan]


#versenum(10) The #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], “Get up! Why have you fallen on your face? 
#versenum(11) #myname[Israel] has sinned; they have transgressed my covenant that I commanded them; they have taken some of the devoted things; they have #underline[stolen] and #underline[lied] and put them among their own #underline[belongings]. 
#versenum(12) Therefore the people of #myname[Israel] cannot stand before their enemies. They turn their backs before their enemies, because they have become devoted for destruction.#footnote[Joshua 7:12 That is, set apart (devoted) as an offering to the Lord (for destruction)] I will be with you no more, #underline[unless] you destroy the devoted things from among you. 
#versenum(13) Get up! Consecrate the people and say, ‘Consecrate yourselves for tomorrow; for thus says the #myhl(divineColor)[#smallcaps[Lord], God of Israel], “There are devoted things in your midst, O #myname[Israel]. You cannot stand before your enemies until you take away the devoted things from among you.” 
#versenum(14) In the morning therefore you shall be brought near by your tribes. And the tribe that the #myhl(divineColor)[#smallcaps[Lord]] takes by lot shall come near by clans. And the clan that the #myhl(divineColor)[#smallcaps[Lord]] takes shall come near by #underline[households]. And the household that the #myhl(divineColor)[#smallcaps[Lord]] takes shall come near man by man. 
#versenum(15) And he who is taken with the devoted things shall be burned with fire, he and all that he has, because he has transgressed the covenant of the #myhl(divineColor)[#smallcaps[Lord]], and because he has done an outrageous thing in #myhl(placesColor)[Israel].’”


  
#versenum(16) So #myhl(menColor)[Joshua] rose early in the morning and brought #myname[Israel] near tribe by tribe, and the tribe of #myname[Judah] was taken. 
#versenum(17) And he brought near the clans of #myname[Judah], and the clan of the #myname[Zerahites] was taken. And he brought near the clan of the #myname[Zerahites] man by man, and #myhl(menColor)[Zabdi] was taken. 
#versenum(18) And he brought near his household man by man, and #myhl(menColor)[Achan] the son of #myhl(menColor)[Carmi], son of #myhl(menColor)[Zabdi], son of #myhl(menColor)[Zerah], of the tribe of #myname[Judah], was taken. 
#versenum(19) Then #myhl(menColor)[Joshua] said to #myhl(menColor)[Achan], “My son, give glory to the #myhl(divineColor)[#smallcaps[Lord] God of Israel] and give #underline[praise]#footnote[Joshua 7:19 Or #emph[and make confession]] to him. And tell me now what you have done; do not hide it from me.” 
#versenum(20) And #myhl(menColor)[Achan] answered #myhl(menColor)[Joshua], “Truly I have sinned against the #myhl(divineColor)[#smallcaps[Lord] God of Israel], and this is what I did: 
#versenum(21) when I saw among the spoil a beautiful cloak from #underline[#myhl(placesColor)[Shinar]], and #mynumber[200] shekels of silver, and a bar of gold #underline[weighing] #underline[#mynumber[50]] shekels,#footnote[Joshua 7:21 A #emph[shekel] was about 2/5 ounce or 11 grams] then I #underline[coveted] them and took them. And see, they are hidden in the earth inside my tent, with the silver underneath.”


  
#versenum(22) So #myhl(menColor)[Joshua] sent messengers, and they ran to the tent; and behold, it was hidden in his tent with the silver underneath. 
#versenum(23) And they took them out of the tent and brought them to #myhl(menColor)[Joshua] and to all the people of #myname[Israel]. And they laid them down before the #myhl(divineColor)[#smallcaps[Lord]]. 
#versenum(24) And #myhl(menColor)[Joshua] and all #myname[Israel] with him took #myhl(menColor)[Achan] the son of #myhl(menColor)[Zerah], and the silver and the cloak and the bar of gold, and his sons and daughters and his oxen and donkeys and sheep and his tent and all that he had. And they brought them up to the #myhl(placesColor)[Valley of Achor]. 
#versenum(25) And #myhl(menColor)[Joshua] said, “Why did you bring trouble on us? The #myhl(divineColor)[#smallcaps[Lord]] #underline[brings] trouble on you today.” And all #myname[Israel] stoned him with stones. They burned them with fire and stoned them with stones. 
#versenum(26) And they raised over him a great heap of stones that remains to this day. Then the #myhl(divineColor)[#smallcaps[Lord]] turned from his #underline[burning] anger. Therefore, to this day the name of that place is called the #myhl(placesColor)[Valley of Achor].#footnote[Joshua 7:26 #emph[Achor] means #emph[trouble]]


  
#chapter-heading[Joshua 8]


#section-heading[The Fall of Ai]


#versenum(1) And the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], “Do not fear and do not be dismayed. Take all the fighting men with you, and arise, go up to #myhl(placesColor)[Ai]. See, I have given into your hand the king of #myhl(placesColor)[Ai], and his people, his city, and his land. 
#versenum(2) And you shall do to #myhl(placesColor)[Ai] and its king as you did to #myhl(placesColor)[Jericho] and its king. Only its spoil and its livestock you shall take as plunder for yourselves. Lay an ambush against the city, behind it.”


  
#versenum(3) So #myhl(menColor)[Joshua] and all the fighting men arose to go up to #myhl(placesColor)[Ai]. And #myhl(menColor)[Joshua] #underline[chose] #underline[#mynumber[30,000]] mighty men of valor and sent them out by night. 
#versenum(4) And he commanded them, “Behold, you shall lie in ambush against the city, behind it. Do not go very far from the city, but all of you remain ready. 
#versenum(5) And I and all the people who are with me will #underline[approach] the city. And when they come out against us just as before, we shall flee before them. 
#versenum(6) And they will come out after us, until we have drawn them away from the city. For they will say, ‘They are #underline[fleeing] from us, just as before.’ So we will flee before them. 
#versenum(7) Then you shall rise up from the ambush and #underline[seize] the city, for the #myhl(divineColor)[#smallcaps[Lord] your God] will give it into your hand. 
#versenum(8) And as soon as you have taken the city, you shall set the city on fire. You shall do according to the word of the #myhl(divineColor)[#smallcaps[Lord]]. See, I have commanded you.” 
#versenum(9) So #myhl(menColor)[Joshua] sent them out. And they went to the place of ambush and lay between #myhl(placesColor)[Bethel] and #myhl(placesColor)[Ai], to the west of #myhl(placesColor)[Ai], but #myhl(menColor)[Joshua] spent that night among the people.


  
#versenum(10) #myhl(menColor)[Joshua] arose early in the morning and mustered the people and went up, he and the elders of #myname[Israel], before the people to #myhl(placesColor)[Ai]. 
#versenum(11) And all the fighting men who were with him went up and drew near before the city and encamped on the north side of #myhl(placesColor)[Ai], with a #underline[ravine] between them and #myhl(placesColor)[Ai]. 
#versenum(12) He took about #underline[#mynumber[5,000]] men and set them in ambush between #myhl(placesColor)[Bethel] and #myhl(placesColor)[Ai], to the west of the city. 
#versenum(13) So they #underline[stationed] the forces, the main #underline[encampment] that was north of the city and its rear guard west of the city. But #myhl(menColor)[Joshua] spent that night in the valley. 
#versenum(14) And as soon as the king of #myhl(placesColor)[Ai] saw this, he and all his people, the men of the city, hurried and went out early to the appointed place#footnote[Joshua 8:14 Hebrew #emph[appointed time]] toward the #myhl(placesColor)[Arabah] to meet #myname[Israel] in battle. But he did not know that there was an ambush against him behind the city. 
#versenum(15) And #myhl(menColor)[Joshua] and all #myname[Israel] #underline[pretended] to be #underline[beaten] before them and fled in the direction of the wilderness. 
#versenum(16) So all the people who were in the city were called together to pursue them, and as they pursued #myhl(menColor)[Joshua] they were drawn away from the city. 
#versenum(17) Not a man was left in #myhl(placesColor)[Ai] or #myhl(placesColor)[Bethel] who did not go out after #myname[Israel]. They left the city open and pursued #myname[Israel].


  
#versenum(18) Then the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], “#underline[Stretch] out the javelin that is in your hand toward #myhl(placesColor)[Ai], for I will give it into your hand.” And #myhl(menColor)[Joshua] stretched out the javelin that was in his hand toward the city. 
#versenum(19) And the men in the ambush rose quickly out of their place, and as soon as he had stretched out his hand, they ran and entered the city and captured it. And they hurried to set the city on fire. 
#versenum(20) So when the men of #myhl(placesColor)[Ai] looked back, behold, the smoke of the city went up to heaven, and they had no power to flee this way or that, for the people who fled to the wilderness turned back against the pursuers. 
#versenum(21) And when #myhl(menColor)[Joshua] and all #myname[Israel] saw that the ambush had captured the city, and that the smoke of the city went up, then they turned back and struck down the men of #myhl(placesColor)[Ai]. 
#versenum(22) And the others came out from the city against them, so they were in the midst of #myname[Israel], some on this side, and some on that side. And #myname[Israel] struck them down, until there was left none that #underline[survived] or escaped. 
#versenum(23) But the king of #myhl(placesColor)[Ai] they took alive, and brought him near to #myhl(menColor)[Joshua].


  
#versenum(24) When #myname[Israel] had finished killing all the inhabitants of #myhl(placesColor)[Ai] in the open wilderness where they pursued them, and all of them to the very last had fallen by the edge of the sword, all #myname[Israel] returned to #myhl(placesColor)[Ai] and struck it down with the edge of the sword. 
#versenum(25) And all who fell that day, both men and women, were #mynumber[12,000], all the people of #myhl(placesColor)[Ai]. 
#versenum(26) But #myhl(menColor)[Joshua] did not draw back his hand with which he stretched out the javelin until he had devoted all the inhabitants of #myhl(placesColor)[Ai] to destruction.#footnote[Joshua 8:26 That is, set apart (devoted) as an offering to the Lord (for destruction)] 
#versenum(27) Only the livestock and the spoil of that city #myname[Israel] took as their plunder, according to the word of the #myhl(divineColor)[#smallcaps[Lord]] that he commanded #myhl(menColor)[Joshua]. 
#versenum(28) So #myhl(menColor)[Joshua] burned #myhl(placesColor)[Ai] and made it forever a heap of #underline[ruins], as it is to this day. 
#versenum(29) And he hanged the king of #myhl(placesColor)[Ai] on a tree until evening. And at #underline[sunset] #myhl(menColor)[Joshua] commanded, and they took his body down from the tree and threw it at the entrance of the gate of the city and raised over it a great heap of stones, which stands there to this day.


  
#section-heading[Joshua Renews the Covenant]


#versenum(30) At that time #myhl(menColor)[Joshua] built an altar to the #myhl(divineColor)[#smallcaps[Lord], the God of Israel], on #myhl(placesColor)[Mount Ebal], 
#versenum(31) just as #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] had commanded the people of #myname[Israel], as it is written in the Book of the Law of #myhl(menColor)[Moses], “an altar of #underline[uncut] stones, upon which no man has #underline[wielded] an iron #underline[tool].” And they offered on it burnt offerings to the #myhl(divineColor)[#smallcaps[Lord]] and sacrificed peace offerings. 
#versenum(32) And there, in the presence of the people of #myname[Israel], he wrote on the stones a copy of the law of #myhl(menColor)[Moses], which he had written. 
#versenum(33) And all #myname[Israel], #underline[sojourner] as well as native born, with their elders and officers and their judges, stood on opposite sides of the ark before the Levitical priests who carried the ark of the covenant of the #myhl(divineColor)[#smallcaps[Lord]], #mynumber[half] of them in front of #myhl(placesColor)[Mount Gerizim] and #mynumber[half] of them in front of #myhl(placesColor)[Mount Ebal], just as #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] had commanded at the first, to bless the people of #myname[Israel]. 
#versenum(34) And afterward he read all the words of the law, the blessing and the curse, according to all that is written in the Book of the Law. 
#versenum(35) There was not a word of all that #myhl(menColor)[Moses] commanded that #myhl(menColor)[Joshua] did not read before all the assembly of #myname[Israel], and the women, and the little ones, and the #underline[sojourners] who lived#footnote[Joshua 8:35 Or #emph[traveled]] among them.


  
#chapter-heading[Joshua 9]


#section-heading[The Gibeonite Deception]


#versenum(1) As soon as all the kings who were beyond the #myhl(placesColor)[Jordan] in the hill country and in the lowland all along the coast of the #myhl(placesColor)[Great Sea] toward #myhl(placesColor)[Lebanon], the #myname[Hittites], the #myname[Amorites], the #myname[Canaanites], the #myname[Perizzites], the #myname[Hivites], and the #myname[Jebusites], heard of this, 
#versenum(2) they gathered together as one to fight against #myhl(menColor)[Joshua] and #myname[Israel].


  
#versenum(3) But when the inhabitants of #myhl(placesColor)[Gibeon] heard what #myhl(menColor)[Joshua] had done to #myhl(placesColor)[Jericho] and to #myhl(placesColor)[Ai], 
#versenum(4) they on their part acted with #underline[cunning] and went and made ready provisions and took worn-out #underline[sacks] for their donkeys, and wineskins, worn-out and #underline[torn] and #underline[mended], 
#versenum(5) with worn-out, #underline[patched] sandals on their feet, and worn-out clothes. And all their provisions were dry and crumbly. 
#versenum(6) And they went to #myhl(menColor)[Joshua] in the camp at #myhl(placesColor)[Gilgal] and said to him and to the men of #myname[Israel], “We have come from a distant country, so now make a covenant with us.” 
#versenum(7) But the men of #myname[Israel] said to the #myname[Hivites], “#underline[Perhaps] you live among us; then how can we make a covenant with you?” 
#versenum(8) They said to #myhl(menColor)[Joshua], “We are your servants.” And #myhl(menColor)[Joshua] said to them, “Who are you? And where do you come from?” 
#versenum(9) They said to him, “From a very distant country your servants have come, because of the name of the #myhl(divineColor)[#smallcaps[Lord] your God]. For we have heard a report of him, and all that he did in #myhl(placesColor)[Egypt], 
#versenum(10) and all that he did to the #mynumber[two] kings of the #myname[Amorites] who were beyond the #myhl(placesColor)[Jordan], to #myhl(menColor)[Sihon] the king of #myhl(placesColor)[Heshbon], and to #myhl(menColor)[Og] king of #myhl(placesColor)[Bashan], who lived in #myhl(placesColor)[Ashtaroth]. 
#versenum(11) So our elders and all the inhabitants of our country said to us, ‘Take provisions in your hand for the journey and go to meet them and say to them, “We are your servants. Come now, make a covenant with us.”’ 
#versenum(12) Here is our bread. It was still #underline[warm] when we took it from our houses as our food for the journey on the day we set out to come to you, but now, behold, it is dry and crumbly. 
#versenum(13) These wineskins were new when we #underline[filled] them, and behold, they have #underline[burst]. And these garments and sandals of ours are worn out from the very long journey.” 
#versenum(14) So the men took some of their provisions, but did not ask counsel from the #myhl(divineColor)[#smallcaps[Lord]]. 
#versenum(15) And #myhl(menColor)[Joshua] made peace with them and made a covenant with them, to let them live, and the leaders of the congregation swore to them.


  
#versenum(16) At the end of #mynumber[three] days after they had made a covenant with them, they heard that they were their #underline[neighbors] and that they lived among them. 
#versenum(17) And the people of #myname[Israel] set out and reached their cities on the #mynumber[third] day. Now their cities were #myhl(placesColor)[Gibeon], #myhl(placesColor)[Chephirah], #myhl(placesColor)[Beeroth], and #myhl(placesColor)[Kiriath-jearim]. 
#versenum(18) But the people of #myname[Israel] did not attack them, because the leaders of the congregation had sworn to them by the #myhl(divineColor)[#smallcaps[Lord], the God of Israel]. Then all the congregation #underline[murmured] against the leaders. 
#versenum(19) But all the leaders said to all the congregation, “We have sworn to them by the #myhl(divineColor)[#smallcaps[Lord], the God of Israel], and now we may not touch them. 
#versenum(20) This we will do to them: let them live, lest wrath be upon us, because of the oath that we swore to them.” 
#versenum(21) And the leaders said to them, “Let them live.” So they became cutters of wood and drawers of water for all the congregation, just as the leaders had said of them.


  
#versenum(22) #myhl(menColor)[Joshua] summoned them, and he said to them, “Why did you #underline[deceive] us, saying, ‘We are very far from you,’ when you dwell among us? 
#versenum(23) Now therefore you are cursed, and some of you shall never be anything but servants, cutters of wood and drawers of water for the house of my #myhl(divineColor)[God].” 
#versenum(24) They answered #myhl(menColor)[Joshua], “Because it was told to your servants for a #underline[certainty] that the #myhl(divineColor)[#smallcaps[Lord] your God] had commanded his servant #myhl(menColor)[Moses] to give you all the land and to destroy all the inhabitants of the land from before you—so we feared greatly for our lives because of you and did this thing. 
#versenum(25) And now, behold, we are in your hand. Whatever seems good and right in your sight to do to us, do it.” 
#versenum(26) So he did this to them and delivered them out of the hand of the people of #myname[Israel], and they did not kill them. 
#versenum(27) But #myhl(menColor)[Joshua] made them that day cutters of wood and drawers of water for the congregation and for the altar of the #myhl(divineColor)[#smallcaps[Lord]], to this day, in the place that he should choose.


  
#chapter-heading[Joshua 10]


#section-heading[The Sun Stands Still]


#versenum(1) As soon as #myhl(menColor)[Adoni-zedek], king of #myhl(placesColor)[Jerusalem], heard how #myhl(menColor)[Joshua] had captured #myhl(placesColor)[Ai] and had devoted it to destruction,#footnote[Joshua 10:1 That is, set apart (devoted) as an offering to the Lord (for destruction); also verses 28, 35, 37, 39, 40] doing to #myhl(placesColor)[Ai] and its king as he had done to #myhl(placesColor)[Jericho] and its king, and how the inhabitants of #myhl(placesColor)[Gibeon] had made peace with #myname[Israel] and were among them, 
#versenum(2) he#footnote[Joshua 10:2 One Hebrew manuscript, Vulgate (compare Syriac); most Hebrew manuscripts #emph[they]] feared greatly, because #myhl(placesColor)[Gibeon] was a great city, like one of the #underline[royal] cities, and because it was greater than #myhl(placesColor)[Ai], and all its men were warriors. 
#versenum(3) So #myhl(menColor)[Adoni-zedek] king of #myhl(placesColor)[Jerusalem] sent to #underline[#myhl(menColor)[Hoham]] king of #myhl(placesColor)[Hebron], to #underline[#myhl(menColor)[Piram]] king of #myhl(placesColor)[Jarmuth], to #myhl(menColor)[Japhia] king of #myhl(placesColor)[Lachish], and to #myhl(menColor)[Debir] king of #myhl(placesColor)[Eglon], saying, 
#versenum(4) “Come up to me and help me, and let us strike #myhl(placesColor)[Gibeon]. For it has made peace with #myhl(menColor)[Joshua] and with the people of #myname[Israel].” 
#versenum(5) Then the #mynumber[five] kings of the #myname[Amorites], the king of #myhl(placesColor)[Jerusalem], the king of #myhl(placesColor)[Hebron], the king of #myhl(placesColor)[Jarmuth], the king of #myhl(placesColor)[Lachish], and the king of #myhl(placesColor)[Eglon], gathered their forces and went up with all their #underline[armies] and encamped against #myhl(placesColor)[Gibeon] and made war against it.


  
#versenum(6) And the men of #myhl(placesColor)[Gibeon] sent to #myhl(menColor)[Joshua] at the camp in #myhl(placesColor)[Gilgal], saying, “Do not #underline[relax] your hand from your servants. Come up to us quickly and save us and help us, for all the kings of the #myname[Amorites] who dwell in the hill country are gathered against us.” 
#versenum(7) So #myhl(menColor)[Joshua] went up from #myhl(placesColor)[Gilgal], he and all the people of war with him, and all the mighty men of valor. 
#versenum(8) And the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], “Do not fear them, for I have given them into your hands. Not a man of them shall stand before you.” 
#versenum(9) So #myhl(menColor)[Joshua] came upon them suddenly, having marched up all night from #myhl(placesColor)[Gilgal]. 
#versenum(10) And the #myhl(divineColor)[#smallcaps[Lord]] threw them into a panic before #myname[Israel], who#footnote[Joshua 10:10 Or #emph[and he]] struck them with a great blow at #myhl(placesColor)[Gibeon] and chased them by the way of the ascent of #myhl(placesColor)[Beth-horon] and struck them as far as #myhl(placesColor)[Azekah] and #myhl(placesColor)[Makkedah]. 
#versenum(11) And as they fled before #myname[Israel], while they were going down the ascent of #myhl(placesColor)[Beth-horon], the #myhl(divineColor)[#smallcaps[Lord]] threw down large stones from heaven on them as far as #myhl(placesColor)[Azekah], and they died. There were more who died because of the #underline[hailstones] than the sons of #myname[Israel] killed with the sword.


  
#versenum(12) At that time #myhl(menColor)[Joshua] spoke to the #myhl(divineColor)[#smallcaps[Lord]] in the day when the #myhl(divineColor)[#smallcaps[Lord]] gave the #myname[Amorites] over to the sons of #myname[Israel], and he said in the sight of #myname[Israel],


    “Sun, stand still at #myhl(placesColor)[Gibeon],\
    #vin and moon, in the #myhl(placesColor)[Valley of Aijalon].”\
    
#versenum(13) And the sun stood still, and the moon stopped,\
    #vin until the nation took vengeance on their enemies.\


      Is this not written in the #myname[Book of #underline[Jashar]]? The sun stopped in the midst of heaven and did not hurry to set for about a whole day. 
#versenum(14) There has been no day like it before or since, when the #myhl(divineColor)[#smallcaps[Lord]] #underline[heeded] the voice of a man, for the #myhl(divineColor)[#smallcaps[Lord]] fought for #myname[Israel].


  
#versenum(15) So #myhl(menColor)[Joshua] returned, and all #myname[Israel] with him, to the camp at #myhl(placesColor)[Gilgal].


  
#section-heading[Five Amorite Kings Executed]


#versenum(16) These #mynumber[five] kings fled and hid themselves in the cave at #myhl(placesColor)[Makkedah]. 
#versenum(17) And it was told to #myhl(menColor)[Joshua], “The #mynumber[five] kings have been found, hidden in the cave at #myhl(placesColor)[Makkedah].” 
#versenum(18) And #myhl(menColor)[Joshua] said, “#underline[Roll] large stones against the mouth of the cave and set men by it to guard them, 
#versenum(19) but do not stay there yourselves. Pursue your enemies; attack their rear guard. Do not let them enter their cities, for the #myhl(divineColor)[#smallcaps[Lord] your God] has given them into your hand.” 
#versenum(20) When #myhl(menColor)[Joshua] and the sons of #myname[Israel] had finished striking them with a great blow until they were #underline[wiped] out, and when the remnant that remained of them had entered into the fortified cities, 
#versenum(21) then all the people returned #underline[safe] to #myhl(menColor)[Joshua] in the camp at #myhl(placesColor)[Makkedah]. Not a man moved his tongue against any of the people of #myname[Israel].


  
#versenum(22) Then #myhl(menColor)[Joshua] said, “Open the mouth of the cave and bring those #mynumber[five] kings out to me from the cave.” 
#versenum(23) And they did so, and brought those #mynumber[five] kings out to him from the cave, the king of #myhl(placesColor)[Jerusalem], the king of #myhl(placesColor)[Hebron], the king of #myhl(placesColor)[Jarmuth], the king of #myhl(placesColor)[Lachish], and the king of #myhl(placesColor)[Eglon]. 
#versenum(24) And when they brought those kings out to #myhl(menColor)[Joshua], #myhl(menColor)[Joshua] summoned all the men of #myname[Israel] and said to the chiefs of the men of war who had gone with him, “Come near; put your feet on the necks of these kings.” Then they came near and put their feet on their necks. 
#versenum(25) And #myhl(menColor)[Joshua] said to them, “Do not be afraid or dismayed; be strong and courageous. For thus the #myhl(divineColor)[#smallcaps[Lord]] will do to all your enemies against whom you fight.” 
#versenum(26) And afterward #myhl(menColor)[Joshua] struck them and put them to death, and he hanged them on #mynumber[five] trees. And they #underline[hung] on the trees until evening. 
#versenum(27) But at the time of the going down of the sun, #myhl(menColor)[Joshua] commanded, and they took them down from the trees and threw them into the cave where they had hidden themselves, and they set large stones against the mouth of the cave, which remain to this very day.


  
#versenum(28) As for #myhl(placesColor)[Makkedah], #myhl(menColor)[Joshua] captured it on that day and struck it, and its king, with the edge of the sword. He devoted to destruction every person in it; he left none remaining. And he did to the king of #myhl(placesColor)[Makkedah] just as he had done to the king of #myhl(placesColor)[Jericho].


  
#section-heading[Conquest of Southern Canaan]


#versenum(29) Then #myhl(menColor)[Joshua] and all #myname[Israel] with him passed on from #myhl(placesColor)[Makkedah] to #myhl(placesColor)[Libnah] and fought against #myhl(placesColor)[Libnah]. 
#versenum(30) And the #myhl(divineColor)[#smallcaps[Lord]] gave it also and its king into the hand of #myname[Israel]. And he struck it with the edge of the sword, and every person in it; he left none remaining in it. And he did to its king as he had done to the king of #myhl(placesColor)[Jericho].


  
#versenum(31) Then #myhl(menColor)[Joshua] and all #myname[Israel] with him passed on from #myhl(placesColor)[Libnah] to #myhl(placesColor)[Lachish] and laid siege to it and fought against it. 
#versenum(32) And the #myhl(divineColor)[#smallcaps[Lord]] gave #myhl(placesColor)[Lachish] into the hand of #myname[Israel], and he captured it on the #mynumber[second] day and struck it with the edge of the sword, and every person in it, as he had done to #myhl(placesColor)[Libnah].


  
#versenum(33) Then #underline[#myhl(menColor)[Horam]] king of #myhl(placesColor)[Gezer] came up to help #myhl(placesColor)[Lachish]. And #myhl(menColor)[Joshua] struck him and his people, until he left none remaining.


  
#versenum(34) Then #myhl(menColor)[Joshua] and all #myname[Israel] with him passed on from #myhl(placesColor)[Lachish] to #myhl(placesColor)[Eglon]. And they laid siege to it and fought against it. 
#versenum(35) And they captured it on that day, and struck it with the edge of the sword. And he devoted every person in it to destruction that day, as he had done to #myhl(placesColor)[Lachish].


  
#versenum(36) Then #myhl(menColor)[Joshua] and all #myname[Israel] with him went up from #myhl(placesColor)[Eglon] to #myhl(placesColor)[Hebron]. And they fought against it 
#versenum(37) and captured it and struck it with the edge of the sword, and its king and its towns, and every person in it. He left none remaining, as he had done to #myhl(placesColor)[Eglon], and devoted it to destruction and every person in it.


  
#versenum(38) Then #myhl(menColor)[Joshua] and all #myname[Israel] with him turned back to #myhl(placesColor)[Debir] and fought against it 
#versenum(39) and he captured it with its king and all its towns. And they struck them with the edge of the sword and devoted to destruction every person in it; he left none remaining. Just as he had done to #myhl(placesColor)[Hebron] and to #myhl(placesColor)[Libnah] and its king, so he did to #myhl(placesColor)[Debir] and to its king.


  
#versenum(40) So #myhl(menColor)[Joshua] struck the whole land, the hill country and the #myhl(placesColor)[Negeb] and the lowland and the slopes, and all their kings. He left none remaining, but devoted to destruction all that breathed, just as the #myhl(divineColor)[#smallcaps[Lord] God of Israel] commanded. 
#versenum(41) And #myhl(menColor)[Joshua] struck them from #myhl(placesColor)[Kadesh-barnea] as far as #myhl(placesColor)[Gaza], and all the country of #myhl(placesColor)[Goshen], as far as #myhl(placesColor)[Gibeon]. 
#versenum(42) And #myhl(menColor)[Joshua] captured all these kings and their land at one time, because the #myhl(divineColor)[#smallcaps[Lord] God of Israel] fought for #myname[Israel]. 
#versenum(43) Then #myhl(menColor)[Joshua] returned, and all #myname[Israel] with him, to the camp at #myhl(placesColor)[Gilgal].


  
#chapter-heading[Joshua 11]


#section-heading[Conquests in Northern Canaan]


#versenum(1) When #myhl(menColor)[Jabin], king of #myhl(placesColor)[Hazor], heard of this, he sent to #underline[#myhl(menColor)[Jobab]] king of #myhl(placesColor)[Madon], and to the king of #myhl(placesColor)[Shimron], and to the king of #myhl(placesColor)[Achshaph], 
#versenum(2) and to the kings who were in the northern hill country, and in the #myhl(placesColor)[Arabah] south of #myhl(placesColor)[Chinneroth], and in the lowland, and in #underline[#myhl(placesColor)[Naphoth-dor]] on the west, 
#versenum(3) to the #myname[Canaanites] in the east and the west, the #myname[Amorites], the #myname[Hittites], the #myname[Perizzites], and the #myname[Jebusites] in the hill country, and the #myname[Hivites] under #myhl(placesColor)[Hermon] in the land of #myhl(placesColor)[Mizpah]. 
#versenum(4) And they came out with all their troops, a great #underline[horde], in number like the sand that is on the seashore, with very many horses and chariots. 
#versenum(5) And all these kings #underline[joined] their forces and came and encamped together at the waters of #myhl(placesColor)[Merom] to fight against #myname[Israel].


  
#versenum(6) And the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], “Do not be afraid of them, for tomorrow at this time I will give over all of them, slain, to #myname[Israel]. You shall #underline[hamstring] their horses and burn their chariots with fire.” 
#versenum(7) So #myhl(menColor)[Joshua] and all his warriors came suddenly against them by the waters of #myhl(placesColor)[Merom] and fell upon them. 
#versenum(8) And the #myhl(divineColor)[#smallcaps[Lord]] gave them into the hand of #myname[Israel], who struck them and chased them as far as Great #myhl(placesColor)[Sidon] and #myhl(placesColor)[Misrephoth-maim], and eastward as far as the #myhl(placesColor)[Valley of Mizpeh]. And they struck them until he left none remaining. 
#versenum(9) And #myhl(menColor)[Joshua] did to them just as the #myhl(divineColor)[#smallcaps[Lord]] said to him: he #underline[hamstrung] their horses and burned their chariots with fire.


  
#versenum(10) And #myhl(menColor)[Joshua] turned back at that time and captured #myhl(placesColor)[Hazor] and struck its king with the sword, for #myhl(placesColor)[Hazor] formerly was the head of all those #underline[kingdoms]. 
#versenum(11) And they struck with the sword all who were in it, devoting them to destruction;#footnote[Joshua 11:11 That is, setting apart (devoting) as an offering to the Lord (for destruction); also verses 12, 20, 21] there was none left that breathed. And he burned #myhl(placesColor)[Hazor] with fire. 
#versenum(12) And all the cities of those kings, and all their kings, #myhl(menColor)[Joshua] captured, and struck them with the edge of the sword, devoting them to destruction, just as #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] had commanded. 
#versenum(13) But none of the cities that stood on #underline[mounds] did #myname[Israel] burn, except #myhl(placesColor)[Hazor] alone; that #myhl(menColor)[Joshua] burned. 
#versenum(14) And all the spoil of these cities and the livestock, the people of #myname[Israel] took for their plunder. But every person they struck with the edge of the sword until they had destroyed them, and they did not leave any who breathed. 
#versenum(15) Just as the #myhl(divineColor)[#smallcaps[Lord]] had commanded #myhl(menColor)[Moses] his servant, so #myhl(menColor)[Moses] commanded #myhl(menColor)[Joshua], and so #myhl(menColor)[Joshua] did. He left nothing #underline[undone] of all that the #myhl(divineColor)[#smallcaps[Lord]] had commanded #myhl(menColor)[Moses].


  
#versenum(16) So #myhl(menColor)[Joshua] took all that land, the hill country and all the #myhl(placesColor)[Negeb] and all the land of #myhl(placesColor)[Goshen] and the lowland and the #myhl(placesColor)[Arabah] and #myhl(placesColor)[the hill country of Israel] and its lowland 
#versenum(17) from #myhl(placesColor)[Mount Halak], which rises toward #myhl(placesColor)[Seir], as far as #myhl(placesColor)[Baal-gad] in the #myhl(placesColor)[Valley of Lebanon] below #myhl(placesColor)[Mount Hermon]. And he captured all their kings and struck them and put them to death. 
#versenum(18) #myhl(menColor)[Joshua] made war a long time with all those kings. 
#versenum(19) There was not a city that made peace with the people of #myname[Israel] except the #myname[Hivites], the inhabitants of #myhl(placesColor)[Gibeon]. They took them all in battle. 
#versenum(20) For it was the #myhl(divineColor)[#smallcaps[Lord]]’s doing to #underline[harden] their hearts that they should come against #myname[Israel] in battle, in order that they should be devoted to destruction and should #underline[receive] no #underline[mercy] but be destroyed, just as the #myhl(divineColor)[#smallcaps[Lord]] commanded #myhl(menColor)[Moses].


  
#versenum(21) And #myhl(menColor)[Joshua] came at that time and cut off the #myname[Anakim] from the hill country, from #myhl(placesColor)[Hebron], from #myhl(placesColor)[Debir], from #myhl(placesColor)[Anab], and from all #myhl(placesColor)[the hill country of Judah], and from all #myhl(placesColor)[the hill country of Israel]. #myhl(menColor)[Joshua] devoted them to destruction with their cities. 
#versenum(22) There was none of the #myname[Anakim] left in the land of the people of #myname[Israel]. Only in #myhl(placesColor)[Gaza], in Gath, and in #myhl(placesColor)[Ashdod] did some remain. 
#versenum(23) So #myhl(menColor)[Joshua] took the whole land, according to all that the #myhl(divineColor)[#smallcaps[Lord]] had spoken to #myhl(menColor)[Moses]. And #myhl(menColor)[Joshua] gave it for an inheritance to #myname[Israel] according to their tribal allotments. And the land had rest from war.


  
#chapter-heading[Joshua 12]


#section-heading[Kings Defeated by Moses]


#versenum(1) Now these are the kings of the land whom the people of #myname[Israel] defeated and took possession of their land beyond the #myhl(placesColor)[Jordan] toward the sunrise, from the #myhl(placesColor)[Valley of the Arnon] to #myhl(placesColor)[Mount Hermon], with all the #myhl(placesColor)[Arabah] eastward: 
#versenum(2) #myhl(menColor)[Sihon] king of the #myname[Amorites] who lived at #myhl(placesColor)[Heshbon] and ruled from #myhl(placesColor)[Aroer], which is on the edge of the #myhl(placesColor)[Valley of the Arnon], and from the middle of the valley as far as the river #myhl(placesColor)[Jabbok], the boundary of the #myname[Ammonites], that is, #mynumber[half] of #myhl(placesColor)[Gilead], 
#versenum(3) and the #myhl(placesColor)[Arabah] to the #myhl(placesColor)[Sea of Chinneroth] eastward, and in the direction of #myhl(placesColor)[Beth-jeshimoth], to the #myhl(placesColor)[Sea of the Arabah], the #myhl(placesColor)[Salt Sea], southward to the foot of the slopes of #myhl(placesColor)[Pisgah]; 
#versenum(4) and #myhl(menColor)[Og]#footnote[Joshua 12:4 Septuagint; Hebrew #emph[the boundary of Og]] king of #myhl(placesColor)[Bashan], one of the remnant of the #myname[Rephaim], who lived at #myhl(placesColor)[Ashtaroth] and at #myhl(placesColor)[Edrei] 
#versenum(5) and ruled over #myhl(placesColor)[Mount Hermon] and #myhl(placesColor)[Salecah] and all #myhl(placesColor)[Bashan] to the boundary of the #myname[Geshurites] and the #myname[Maacathites], and over #mynumber[half] of #myhl(placesColor)[Gilead] to the boundary of #myhl(menColor)[Sihon] king of #myhl(placesColor)[Heshbon]. 
#versenum(6) #myhl(menColor)[Moses], the servant of the #myhl(divineColor)[#smallcaps[Lord]], and the people of #myname[Israel] defeated them. And #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] gave their land for a possession to the #myname[Reubenites] and the #myname[Gadites] and the #mynumber[half]-tribe of #myname[Manasseh].


  
#section-heading[Kings Defeated by Joshua]


#versenum(7) And these are the kings of the land whom #myhl(menColor)[Joshua] and the people of #myname[Israel] defeated on the west side of the #myhl(placesColor)[Jordan], from #myhl(placesColor)[Baal-gad] in the #myhl(placesColor)[Valley of Lebanon] to #myhl(placesColor)[Mount Halak], that rises toward #myhl(placesColor)[Seir] (and #myhl(menColor)[Joshua] gave their land to the tribes of #myname[Israel] as a possession according to their allotments, 
#versenum(8) in the hill country, in the lowland, in the #myhl(placesColor)[Arabah], in the slopes, in the wilderness, and in the #myhl(placesColor)[Negeb], the land of the #myname[Hittites], the #myname[Amorites], the #myname[Canaanites], the #myname[Perizzites], the #myname[Hivites], and the #myname[Jebusites]): 
#versenum(9) the king of #myhl(placesColor)[Jericho], #mynumber[one]; the king of #myhl(placesColor)[Ai], which is beside #myhl(placesColor)[Bethel], #mynumber[one]; 
#versenum(10) the king of #myhl(placesColor)[Jerusalem], #mynumber[one]; the king of #myhl(placesColor)[Hebron], #mynumber[one]; 
#versenum(11) the king of #myhl(placesColor)[Jarmuth], #mynumber[one]; the king of #myhl(placesColor)[Lachish], #mynumber[one]; 
#versenum(12) the king of #myhl(placesColor)[Eglon], #mynumber[one]; the king of #myhl(placesColor)[Gezer], #mynumber[one]; 
#versenum(13) the king of #myhl(placesColor)[Debir], #mynumber[one]; the king of #underline[#myhl(placesColor)[Geder]], #mynumber[one]; 
#versenum(14) the king of #myhl(placesColor)[Hormah], #mynumber[one]; the king of #myhl(placesColor)[Arad], #mynumber[one]; 
#versenum(15) the king of #myhl(placesColor)[Libnah], #mynumber[one]; the king of #myhl(placesColor)[Adullam], #mynumber[one]; 
#versenum(16) the king of #myhl(placesColor)[Makkedah], #mynumber[one]; the king of #myhl(placesColor)[Bethel], #mynumber[one]; 
#versenum(17) the king of #myhl(placesColor)[Tappuah], #mynumber[one]; the king of #myhl(placesColor)[Hepher], #mynumber[one]; 
#versenum(18) the king of #myhl(placesColor)[Aphek], #mynumber[one]; the king of #underline[#myhl(placesColor)[Lasharon]], #mynumber[one]; 
#versenum(19) the king of #myhl(placesColor)[Madon], #mynumber[one]; the king of #myhl(placesColor)[Hazor], #mynumber[one]; 
#versenum(20) the king of #underline[#myhl(placesColor)[Shimron-meron]], #mynumber[one]; the king of #myhl(placesColor)[Achshaph], #mynumber[one]; 
#versenum(21) the king of #myhl(placesColor)[Taanach], #mynumber[one]; the king of #myhl(placesColor)[Megiddo], #mynumber[one]; 
#versenum(22) the king of #myhl(placesColor)[Kedesh], #mynumber[one]; the king of #myhl(placesColor)[Jokneam] in #myhl(placesColor)[Carmel], #mynumber[one]; 
#versenum(23) the king of #myhl(placesColor)[Dor] in #underline[#myhl(placesColor)[Naphath-dor]], #mynumber[one]; the king of #underline[#myhl(placesColor)[Goiim]] in #myhl(placesColor)[Galilee],#footnote[Joshua 12:23 Septuagint; Hebrew #emph[Gilgal]] #mynumber[one]; 
#versenum(24) the king of #myhl(placesColor)[Tirzah], #mynumber[one]: in all, #underline[#mynumber[thirty-one]] kings.


  
#chapter-heading[Joshua 13]


#section-heading[Land Still to Be Conquered]


#versenum(1) Now #myhl(menColor)[Joshua] was old and advanced in years, and the #myhl(divineColor)[#smallcaps[Lord]] said to him, “You are old and advanced in years, and there remains yet very much land to possess. 
#versenum(2) This is the land that yet remains: all the #underline[regions] of the #myname[Philistines], and all those of the #myname[Geshurites] 
#versenum(3) (from the #underline[#myhl(placesColor)[Shihor]], which is east of #myhl(placesColor)[Egypt], northward to the boundary of #myhl(placesColor)[Ekron], it is counted as #underline[#myname[Canaanite]]; there are #mynumber[five] rulers of the #myname[Philistines], those of #myhl(placesColor)[Gaza], #myhl(placesColor)[Ashdod], #myhl(placesColor)[Ashkelon], Gath, and #myhl(placesColor)[Ekron]), and those of the #myname[Avvim], 
#versenum(4) in the south, all the land of the #myname[Canaanites], and #underline[#myhl(placesColor)[Mearah]] that belongs to the #myname[Sidonians], to #myhl(placesColor)[Aphek], to the boundary of the #myname[Amorites], 
#versenum(5) and the land of the #underline[#myname[Gebalites]], and all #myhl(placesColor)[Lebanon], toward the sunrise, from #myhl(placesColor)[Baal-gad] below #myhl(placesColor)[Mount Hermon] to #myhl(placesColor)[Lebo-hamath], 
#versenum(6) all the inhabitants of the hill country from #myhl(placesColor)[Lebanon] to #myhl(placesColor)[Misrephoth-maim], even all the #myname[Sidonians]. I myself will drive them out from before the people of #myname[Israel]. Only #underline[allot] the land to #myname[Israel] for an inheritance, as I have commanded you. 
#versenum(7) Now therefore divide this land for an inheritance to the #mynumber[nine] tribes and #mynumber[half] the tribe of #myname[Manasseh].”


  
#section-heading[The Inheritance East of the Jordan]


#versenum(8) With the other #mynumber[half] of the tribe of #myname[Manasseh]#footnote[Joshua 13:8 Hebrew #emph[With it]] the #myname[Reubenites] and the #myname[Gadites] received their inheritance, which #myhl(menColor)[Moses] gave them, beyond the #myhl(placesColor)[Jordan] eastward, as #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] gave them: 
#versenum(9) from #myhl(placesColor)[Aroer], which is on the edge of the #myhl(placesColor)[Valley of the Arnon], and the city that is in the middle of the valley, and all the tableland of #myhl(placesColor)[Medeba] as far as #myhl(placesColor)[Dibon]; 
#versenum(10) and all the cities of #myhl(menColor)[Sihon] king of the #myname[Amorites], who reigned in #myhl(placesColor)[Heshbon], as far as the boundary of the #myname[Ammonites]; 
#versenum(11) and #myhl(placesColor)[Gilead], and the region of the #myname[Geshurites] and #myname[Maacathites], and all #myhl(placesColor)[Mount Hermon], and all #myhl(placesColor)[Bashan] to #myhl(placesColor)[Salecah]; 
#versenum(12) all the kingdom of #myhl(menColor)[Og] in #myhl(placesColor)[Bashan], who reigned in #myhl(placesColor)[Ashtaroth] and in #myhl(placesColor)[Edrei] (he alone was left of the remnant of the #myname[Rephaim]); these #myhl(menColor)[Moses] had struck and driven out. 
#versenum(13) Yet the people of #myname[Israel] did not drive out the #myname[Geshurites] or the #myname[Maacathites], but #underline[#myhl(placesColor)[Geshur]] and #underline[#myhl(placesColor)[Maacath]] dwell in the midst of #myname[Israel] to this day.


  
#versenum(14) To the tribe of #myname[Levi] alone #myhl(menColor)[Moses] gave no inheritance. The offerings by fire to the #myhl(divineColor)[#smallcaps[Lord] God of Israel] are their inheritance, as he said to him.


  
#versenum(15) And #myhl(menColor)[Moses] gave an inheritance to the tribe of the people of #myname[Reuben] according to their clans. 
#versenum(16) So their territory was from #myhl(placesColor)[Aroer], which is on the edge of the #myhl(placesColor)[Valley of the Arnon], and the city that is in the middle of the valley, and all the tableland by #myhl(placesColor)[Medeba]; 
#versenum(17) with #myhl(placesColor)[Heshbon], and all its cities that are in the tableland; #myhl(placesColor)[Dibon], and #underline[#myhl(placesColor)[Bamoth-baal]], and #underline[#myhl(placesColor)[Beth-baal-meon]], 
#versenum(18) and #myhl(placesColor)[Jahaz], and #myhl(placesColor)[Kedemoth], and #myhl(placesColor)[Mephaath], 
#versenum(19) and #underline[#myhl(placesColor)[Kiriathaim]], and #underline[#myhl(placesColor)[Sibmah]], and #underline[#myhl(placesColor)[Zereth-shahar]] on the hill of the valley, 
#versenum(20) and #underline[#myhl(placesColor)[Beth-peor]], and the slopes of #myhl(placesColor)[Pisgah], and #myhl(placesColor)[Beth-jeshimoth], 
#versenum(21) that is, all the cities of the tableland, and all the kingdom of #myhl(menColor)[Sihon] king of the #myname[Amorites], who reigned in #myhl(placesColor)[Heshbon], whom #myhl(menColor)[Moses] defeated with the leaders of #myname[Midian], #underline[#myhl(menColor)[Evi]] and #myhl(menColor)[Rekem] and #underline[#myhl(menColor)[Zur]] and #underline[#myhl(menColor)[Hur]] and #underline[#myhl(menColor)[Reba]], the princes of #myhl(menColor)[Sihon], who lived in the land. 
#versenum(22) #myhl(menColor)[Balaam] also, the son of #myhl(menColor)[Beor], the one who #underline[practiced] #underline[divination], was killed with the sword by the people of #myname[Israel] among the rest of their slain. 
#versenum(23) And the border of the people of #myname[Reuben] was the #myhl(placesColor)[Jordan] as a boundary. This was the inheritance of the people of #myname[Reuben], according to their clans with their cities and villages.


  
#versenum(24) #myhl(menColor)[Moses] gave an inheritance also to the tribe of #myname[Gad], to the people of #myname[Gad], according to their clans. 
#versenum(25) Their territory was #myhl(placesColor)[Jazer], and all the cities of #myhl(placesColor)[Gilead], and #mynumber[half] the land of the #myname[Ammonites], to #myhl(placesColor)[Aroer], which is east of #myhl(placesColor)[Rabbah], 
#versenum(26) and from #myhl(placesColor)[Heshbon] to #underline[#myhl(placesColor)[Ramath-mizpeh]] and #underline[#myhl(placesColor)[Betonim]], and from #myhl(placesColor)[Mahanaim] to the territory of #myhl(placesColor)[Debir],#footnote[Joshua 13:26 Septuagint, Syriac, Vulgate; Hebrew #emph[Lidebir]] 
#versenum(27) and in the valley #underline[#myhl(placesColor)[Beth-haram]], #underline[#myhl(placesColor)[Beth-nimrah]], #myhl(placesColor)[Succoth], and #myhl(placesColor)[Zaphon], the rest of the kingdom of #myhl(menColor)[Sihon] king of #myhl(placesColor)[Heshbon], having the #myhl(placesColor)[Jordan] as a boundary, to the lower end of the #myhl(placesColor)[Sea of Chinnereth], eastward beyond the #myhl(placesColor)[Jordan]. 
#versenum(28) This is the inheritance of the people of #myname[Gad] according to their clans, with their cities and villages.


  
#versenum(29) And #myhl(menColor)[Moses] gave an inheritance to the #mynumber[half]-tribe of #myname[Manasseh]. It was allotted to the #mynumber[half]-tribe of the people of #myname[Manasseh] according to their clans. 
#versenum(30) Their region #underline[extended] from #myhl(placesColor)[Mahanaim], through all #myhl(placesColor)[Bashan], the whole kingdom of #myhl(menColor)[Og] king of #myhl(placesColor)[Bashan], and all the towns of #myhl(menColor)[Jair], which are in #myhl(placesColor)[Bashan], #underline[#mynumber[sixty]] cities, 
#versenum(31) and #mynumber[half] #myhl(placesColor)[Gilead], and #myhl(placesColor)[Ashtaroth], and #myhl(placesColor)[Edrei], the cities of the kingdom of #myhl(menColor)[Og] in #myhl(placesColor)[Bashan]. These were allotted to the people of #myhl(menColor)[Machir] the son of #myhl(menColor)[Manasseh] for the #mynumber[half] of the people of #myhl(menColor)[Machir] according to their clans.


  
#versenum(32) These are the inheritances that #myhl(menColor)[Moses] distributed in the plains of #myhl(placesColor)[Moab], beyond the #myhl(placesColor)[Jordan] east of #myhl(placesColor)[Jericho]. 
#versenum(33) But to the tribe of #myname[Levi] #myhl(menColor)[Moses] gave no inheritance; the #myhl(divineColor)[#smallcaps[Lord] God of Israel] is their inheritance, just as he said to them.


  
#chapter-heading[Joshua 14]


#section-heading[The Inheritance West of the Jordan]


#versenum(1) These are the inheritances that the people of #myname[Israel] received in the land of #myhl(placesColor)[Canaan], which #myhl(menColor)[Eleazar] the priest and #myhl(menColor)[Joshua] the son of #myhl(menColor)[Nun] and the heads of the fathers’ houses of the tribes of the people of #myname[Israel] gave them to inherit. 
#versenum(2) Their inheritance was by lot, just as the #myhl(divineColor)[#smallcaps[Lord]] had commanded by the hand of #myhl(menColor)[Moses] for the #mynumber[nine and one-half] tribes. 
#versenum(3) For #myhl(menColor)[Moses] had given an inheritance to the #mynumber[two and one-half] tribes beyond the #myhl(placesColor)[Jordan], but to the #myname[Levites] he gave no inheritance among them. 
#versenum(4) For the people of #myname[Joseph] were #mynumber[two] tribes, #myname[Manasseh] and #myname[Ephraim]. And no portion was given to the #myname[Levites] in the land, but only cities to dwell in, with their pasturelands for their livestock and their #underline[substance]. 
#versenum(5) The people of #myname[Israel] did as the #myhl(divineColor)[#smallcaps[Lord]] commanded #myhl(menColor)[Moses]; they allotted the land.


  
#section-heading[Caleb’s Request and Inheritance]


#versenum(6) Then the people of #myname[Judah] came to #myhl(menColor)[Joshua] at #myhl(placesColor)[Gilgal]. And #myhl(menColor)[Caleb] the son of #myhl(menColor)[Jephunneh] the #myname[Kenizzite] said to him, “You know what the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Moses] the man of #myhl(divineColor)[God] in #myhl(placesColor)[Kadesh-barnea] concerning you and me. 
#versenum(7) I was #mynumber[forty] years old when #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] sent me from #myhl(placesColor)[Kadesh-barnea] to spy out the land, and I brought him word again as it was in my heart. 
#versenum(8) But my brothers who went up with me made the heart of the people melt; yet I wholly followed the #myhl(divineColor)[#smallcaps[Lord]] my #myhl(divineColor)[God]. 
#versenum(9) And #myhl(menColor)[Moses] swore on that day, saying, ‘Surely the land on which your foot has #underline[trodden] shall be an inheritance for you and your children forever, because you have wholly followed the #myhl(divineColor)[#smallcaps[Lord]] my #myhl(divineColor)[God].’ 
#versenum(10) And now, behold, the #myhl(divineColor)[#smallcaps[Lord]] has kept me alive, just as he said, these #underline[#mynumber[forty-five]] years since the time that the #myhl(divineColor)[#smallcaps[Lord]] spoke this word to #myhl(menColor)[Moses], while #myname[Israel] walked in the wilderness. And now, behold, I am this day #underline[#mynumber[eighty-five]] years old. 
#versenum(11) I am still as strong today as I was in the day that #myhl(menColor)[Moses] sent me; my strength now is as my strength was then, for war and for going and coming. 
#versenum(12) So now give me this hill country of which the #myhl(divineColor)[#smallcaps[Lord]] spoke on that day, for you heard on that day how the #myname[Anakim] were there, with great fortified cities. It may be that the #myhl(divineColor)[#smallcaps[Lord]] will be with me, and I shall drive them out just as the #myhl(divineColor)[#smallcaps[Lord]] said.”


  
#versenum(13) Then #myhl(menColor)[Joshua] blessed him, and he gave #myhl(placesColor)[Hebron] to #myhl(menColor)[Caleb] the son of #myhl(menColor)[Jephunneh] for an inheritance. 
#versenum(14) Therefore #myhl(placesColor)[Hebron] became the inheritance of #myhl(menColor)[Caleb] the son of #myhl(menColor)[Jephunneh] the #myname[Kenizzite] to this day, because he wholly followed the #myhl(divineColor)[#smallcaps[Lord], the God of Israel]. 
#versenum(15) Now the name of #myhl(placesColor)[Hebron] formerly was #myhl(placesColor)[Kiriath-arba].#footnote[Joshua 14:15 #emph[Kiriath-arba] means #emph[the city of Arba]] (#myhl(menColor)[Arba]#footnote[Joshua 14:15 Hebrew #emph[He]] was the #underline[greatest] man among the #myname[Anakim].) And the land had rest from war.


  
#chapter-heading[Joshua 15]


#section-heading[The Allotment for Judah]


#versenum(1) The allotment for the tribe of the people of #myname[Judah] according to their clans reached southward to the boundary of #myhl(placesColor)[Edom], to the wilderness of #myhl(placesColor)[Zin] at the farthest south. 
#versenum(2) And their south boundary ran from the end of the #myhl(placesColor)[Salt Sea], from the bay that faces southward. 
#versenum(3) It goes out southward of the ascent of #myhl(placesColor)[Akrabbim], passes along to #myhl(placesColor)[Zin], and goes up south of #myhl(placesColor)[Kadesh-barnea], along by #myhl(placesColor)[Hezron], up to #underline[#myhl(placesColor)[Addar]], turns about to #underline[#myhl(placesColor)[Karka]], 
#versenum(4) passes along to #underline[#myhl(placesColor)[Azmon]], goes out by the #myhl(placesColor)[Brook of Egypt], and comes to its end at the sea. This shall be your south boundary. 
#versenum(5) And the east boundary is the #myhl(placesColor)[Salt Sea], to the mouth of the #myhl(placesColor)[Jordan]. And the boundary on the north side #underline[runs] from the bay of the sea at the mouth of the #myhl(placesColor)[Jordan]. 
#versenum(6) And the boundary goes up to #myhl(placesColor)[Beth-hoglah] and passes along north of #myhl(placesColor)[Beth-arabah]. And the boundary goes up to the stone of #myhl(menColor)[Bohan] the son of #myhl(menColor)[Reuben]. 
#versenum(7) And the boundary goes up to #myhl(placesColor)[Debir] from the #myhl(placesColor)[Valley of Achor], and so northward, turning toward #myhl(placesColor)[Gilgal], which is opposite the ascent of #myhl(placesColor)[Adummim], which is on the south side of the valley. And the boundary passes along to the waters of #myhl(placesColor)[En-shemesh] and ends at #myhl(placesColor)[En-rogel]. 
#versenum(8) Then the boundary goes up by the #myhl(placesColor)[Valley of the Son of Hinnom] at the southern shoulder of the #underline[#myname[Jebusite]] (that is, #myhl(placesColor)[Jerusalem]). And the boundary goes up to the top of the mountain that lies over against the #myhl(placesColor)[Valley of Hinnom], on the west, at the northern end of the #myhl(placesColor)[Valley of Rephaim]. 
#versenum(9) Then the boundary #underline[extends] from the top of the mountain to the spring of the waters of #myhl(placesColor)[Nephtoah], and from there to the cities of #myhl(placesColor)[Mount Ephron]. Then the boundary bends around to #myhl(placesColor)[Baalah] (that is, #myhl(placesColor)[Kiriath-jearim]). 
#versenum(10) And the boundary #underline[circles] west of #myhl(placesColor)[Baalah] to #myhl(placesColor)[Mount Seir], passes along to the northern shoulder of #myhl(placesColor)[Mount #underline[Jearim]] (that is, #underline[#myhl(placesColor)[Chesalon]]), and goes down to #myhl(placesColor)[Beth-shemesh] and passes along by #myhl(placesColor)[Timnah]. 
#versenum(11) The boundary goes out to the shoulder of the hill north of #myhl(placesColor)[Ekron], then the boundary bends around to #underline[#myhl(placesColor)[Shikkeron]] and passes along to #myhl(placesColor)[Mount Baalah] and goes out to #myhl(placesColor)[Jabneel]. Then the boundary comes to an end at the sea. 
#versenum(12) And the west boundary was the #myhl(placesColor)[Great Sea] with its coastline. This is the boundary around the people of #myname[Judah] according to their clans.


  
#versenum(13) According to the commandment of the #myhl(divineColor)[#smallcaps[Lord]] to #myhl(menColor)[Joshua], he gave to #myhl(menColor)[Caleb] the son of #myhl(menColor)[Jephunneh] a portion among the people of #myname[Judah], #myhl(placesColor)[Kiriath-arba], that is, #myhl(placesColor)[Hebron] (#myhl(menColor)[Arba] was the father of #myhl(menColor)[Anak]). 
#versenum(14) And #myhl(menColor)[Caleb] drove out from there the #mynumber[three] sons of #myhl(menColor)[Anak], #myhl(menColor)[Sheshai] and #myhl(menColor)[Ahiman] and #myhl(menColor)[Talmai], the descendants of #myhl(menColor)[Anak]. 
#versenum(15) And he went up from there against the inhabitants of #myhl(placesColor)[Debir]. Now the name of #myhl(placesColor)[Debir] formerly was #myhl(placesColor)[Kiriath-sepher]. 
#versenum(16) And #myhl(menColor)[Caleb] said, “He who attacks #myhl(placesColor)[Kiriath-sepher] and captures it, I will give him #myhl(womenColor)[Achsah] my daughter as wife.” 
#versenum(17) And #myhl(menColor)[Othniel] the son of #myhl(menColor)[Kenaz], the brother of #myhl(menColor)[Caleb], captured it. And he gave him #myhl(womenColor)[Achsah] his daughter as wife. 
#versenum(18) When she came to him, she urged him to ask her father for a field. And she dismounted from her donkey, and #myhl(menColor)[Caleb] said to her, “What do you want?” 
#versenum(19) She said to him, “Give me a blessing. Since you have given me the land of the #myhl(placesColor)[Negeb], give me also springs of water.” And he gave her the upper springs and the lower springs.


  
#versenum(20) This is the inheritance of the tribe of the people of #myname[Judah] according to their clans. 
#versenum(21) The cities belonging to the tribe of the people of #myname[Judah] in the #underline[extreme] south, toward the boundary of #myhl(placesColor)[Edom], were #underline[#myhl(placesColor)[Kabzeel]], #underline[#myhl(placesColor)[Eder]], #underline[#myhl(placesColor)[Jagur]], 
#versenum(22) #underline[#myhl(placesColor)[Kinah]], #underline[#myhl(placesColor)[Dimonah]], #underline[#myhl(placesColor)[Adadah]], 
#versenum(23) #myhl(placesColor)[Kedesh], #myhl(placesColor)[Hazor], #underline[#myhl(placesColor)[Ithnan]], 
#versenum(24) #myhl(placesColor)[Ziph], #underline[#myhl(placesColor)[Telem]], #underline[#myhl(placesColor)[Bealoth]], 
#versenum(25) #underline[#myhl(placesColor)[Hazor-hadattah]], #underline[#myhl(placesColor)[Kerioth-hezron]] (that is, #myhl(placesColor)[Hazor]), 
#versenum(26) #underline[#myhl(placesColor)[Amam]], #underline[#myhl(placesColor)[Shema]], #myhl(placesColor)[Moladah], 
#versenum(27) #underline[#myhl(placesColor)[Hazar-gaddah]], #underline[#myhl(placesColor)[Heshmon]], #underline[#myhl(placesColor)[Beth-pelet]], 
#versenum(28) #myhl(placesColor)[Hazar-shual], #myhl(placesColor)[Beersheba], #underline[#myhl(placesColor)[Biziothiah]], 
#versenum(29) #myhl(placesColor)[Baalah], #underline[#myhl(placesColor)[Iim]], #myhl(placesColor)[Ezem], 
#versenum(30) #myhl(placesColor)[Eltolad], #underline[#myhl(placesColor)[Chesil]], #myhl(placesColor)[Hormah], 
#versenum(31) #myhl(placesColor)[Ziklag], #underline[#myhl(placesColor)[Madmannah]], #underline[#myhl(placesColor)[Sansannah]], 
#versenum(32) #underline[#myhl(placesColor)[Lebaoth]], #underline[#myhl(placesColor)[Shilhim]], #myhl(placesColor)[Ain], and #myhl(placesColor)[Rimmon]: in all, #underline[#mynumber[twenty-nine]] cities with their villages.


  
#versenum(33) And in the lowland, #myhl(placesColor)[Eshtaol], #myhl(placesColor)[Zorah], #myhl(placesColor)[Ashnah], 
#versenum(34) #myhl(placesColor)[Zanoah], #myhl(placesColor)[En-gannim], #myhl(placesColor)[Tappuah], #underline[#myhl(placesColor)[Enam]], 
#versenum(35) #myhl(placesColor)[Jarmuth], #myhl(placesColor)[Adullam], #myhl(placesColor)[Socoh], #myhl(placesColor)[Azekah], 
#versenum(36) #underline[#myhl(placesColor)[Shaaraim]], #underline[#myhl(placesColor)[Adithaim]], #underline[#myhl(placesColor)[Gederah]], #underline[#myhl(placesColor)[Gederothaim]]: #mynumber[fourteen] cities with their villages.


  
#versenum(37) #underline[#myhl(placesColor)[Zenan]], #underline[#myhl(placesColor)[Hadashah]], #underline[#myhl(placesColor)[Migdal-gad]], 
#versenum(38) #underline[#myhl(placesColor)[Dilean]], #myhl(placesColor)[Mizpeh], #underline[#myhl(placesColor)[Joktheel]], 
#versenum(39) #myhl(placesColor)[Lachish], #underline[#myhl(placesColor)[Bozkath]], #myhl(placesColor)[Eglon], 
#versenum(40) #underline[#myhl(placesColor)[Cabbon]], #underline[#myhl(placesColor)[Lahmam]], #underline[#myhl(placesColor)[Chitlish]], 
#versenum(41) #underline[#myhl(placesColor)[Gederoth]], #myhl(placesColor)[Beth-dagon], #underline[#myhl(placesColor)[Naamah]], and #myhl(placesColor)[Makkedah]: #mynumber[sixteen] cities with their villages.


  
#versenum(42) #myhl(placesColor)[Libnah], #myhl(placesColor)[Ether], #myhl(placesColor)[Ashan], 
#versenum(43) #underline[#myhl(placesColor)[Iphtah]], #myhl(placesColor)[Ashnah], #underline[#myhl(placesColor)[Nezib]], 
#versenum(44) #underline[#myhl(placesColor)[Keilah]], #myhl(placesColor)[Achzib], and #underline[#myhl(placesColor)[Mareshah]]: #mynumber[nine] cities with their villages.


  
#versenum(45) #myhl(placesColor)[Ekron], with its towns and its villages; 
#versenum(46) from #myhl(placesColor)[Ekron] to the sea, all that were by the side of #myhl(placesColor)[Ashdod], with their villages.


  
#versenum(47) #myhl(placesColor)[Ashdod], its towns and its villages; #myhl(placesColor)[Gaza], its towns and its villages; to the #myhl(placesColor)[Brook of Egypt], and the #myhl(placesColor)[Great Sea] with its coastline.


  
#versenum(48) And in the hill country, #myhl(placesColor)[Shamir], #myhl(placesColor)[Jattir], #myhl(placesColor)[Socoh], 
#versenum(49) #underline[#myhl(placesColor)[Dannah]], #underline[#myhl(placesColor)[Kiriath-sannah]] (that is, #myhl(placesColor)[Debir]), 
#versenum(50) #myhl(placesColor)[Anab], #underline[#myhl(placesColor)[Eshtemoh]], #underline[#myhl(placesColor)[Anim]], 
#versenum(51) #myhl(placesColor)[Goshen], #myhl(placesColor)[Holon], and #underline[#myhl(placesColor)[Giloh]]: #underline[#mynumber[eleven]] cities with their villages.


  
#versenum(52) #underline[#myhl(placesColor)[Arab]], #underline[#myhl(placesColor)[Dumah]], #underline[#myhl(placesColor)[Eshan]], 
#versenum(53) #underline[#myhl(placesColor)[Janim]], #underline[#myhl(placesColor)[Beth-tappuah]], #underline[#myhl(placesColor)[Aphekah]], 
#versenum(54) #underline[#myhl(placesColor)[Humtah]], #myhl(placesColor)[Kiriath-arba] (that is, #myhl(placesColor)[Hebron]), and #underline[#myhl(placesColor)[Zior]]: #mynumber[nine] cities with their villages.


  
#versenum(55) #underline[#myhl(placesColor)[Maon]], #myhl(placesColor)[Carmel], #myhl(placesColor)[Ziph], #myhl(placesColor)[Juttah], 
#versenum(56) #myhl(placesColor)[Jezreel], #underline[#myhl(placesColor)[Jokdeam]], #myhl(placesColor)[Zanoah], 
#versenum(57) #underline[#myhl(placesColor)[Kain]], #myhl(placesColor)[Gibeah], and #myhl(placesColor)[Timnah]: #mynumber[ten] cities with their villages.


  
#versenum(58) #underline[#myhl(placesColor)[Halhul]], #underline[#myhl(placesColor)[Beth-zur]], #underline[#myhl(placesColor)[Gedor]], 
#versenum(59) #underline[#myhl(placesColor)[Maarath]], #underline[#myhl(placesColor)[Beth-anoth]], and #underline[#myhl(placesColor)[Eltekon]]: #mynumber[six] cities with their villages.


  
#versenum(60) #myhl(placesColor)[Kiriath-baal] (that is, #myhl(placesColor)[Kiriath-jearim]), and #myhl(placesColor)[Rabbah]: #mynumber[two] cities with their villages.


  
#versenum(61) In the wilderness, #myhl(placesColor)[Beth-arabah], #underline[#myhl(placesColor)[Middin]], #underline[#myhl(placesColor)[Secacah]], 
#versenum(62) #underline[#myhl(placesColor)[Nibshan]], the #myhl(placesColor)[City of Salt], and #underline[#myhl(placesColor)[Engedi]]: #mynumber[six] cities with their villages.


  
#versenum(63) But the #myname[Jebusites], the inhabitants of #myhl(placesColor)[Jerusalem], the people of #myname[Judah] could not drive out, so the #myname[Jebusites] dwell with the people of #myname[Judah] at #myhl(placesColor)[Jerusalem] to this day.


  
#chapter-heading[Joshua 16]


#section-heading[The Allotment for Ephraim and Manasseh]


#versenum(1) The allotment of the people of #myname[Joseph] went from the #myhl(placesColor)[Jordan] by #myhl(placesColor)[Jericho], east of the waters of #myhl(placesColor)[Jericho], into the wilderness, going up from #myhl(placesColor)[Jericho] into the hill country to #myhl(placesColor)[Bethel]. 
#versenum(2) Then going from #myhl(placesColor)[Bethel] to #myhl(placesColor)[Luz], it passes along to #myhl(placesColor)[Ataroth], the territory of the #underline[#myname[Archites]]. 
#versenum(3) Then it goes down westward to the territory of the #underline[#myname[Japhletites]], as far as the territory of #myhl(placesColor)[Lower Beth-horon], then to #myhl(placesColor)[Gezer], and it ends at the sea.


  
#versenum(4) The people of #myname[Joseph], #myname[Manasseh] and #myname[Ephraim], received their inheritance.


  
#versenum(5) The territory of the people of #myname[Ephraim] by their clans was as #underline[follows]: the boundary of their inheritance on the east was #myhl(placesColor)[Ataroth-addar] as far as #myhl(placesColor)[Upper Beth-horon], 
#versenum(6) and the boundary goes from there to the sea. On the north is #myhl(placesColor)[Michmethath]. Then on the east the boundary turns around toward #underline[#myhl(placesColor)[Taanath-shiloh]] and passes along beyond it on the east to #myhl(placesColor)[Janoah], 
#versenum(7) then it goes down from #myhl(placesColor)[Janoah] to #myhl(placesColor)[Ataroth] and to #underline[#myhl(placesColor)[Naarah]], and touches #myhl(placesColor)[Jericho], #underline[ending] at the #myhl(placesColor)[Jordan]. 
#versenum(8) From #myhl(placesColor)[Tappuah] the boundary goes westward to the brook #myhl(placesColor)[Kanah] and ends at the sea. Such is the inheritance of the tribe of the people of #myname[Ephraim] by their clans, 
#versenum(9) together with the towns that were set apart for the people of #myname[Ephraim] within the inheritance of the #underline[#myname[Manassites]], all those towns with their villages. 
#versenum(10) #underline[However], they did not drive out the #myname[Canaanites] who lived in #myhl(placesColor)[Gezer], so the #myname[Canaanites] have lived in the midst of #myname[Ephraim] to this day but have been made to do forced labor.


  
#chapter-heading[Joshua 17]


#versenum(1) Then allotment was made to the people of #myname[Manasseh], for he was the firstborn of #myhl(menColor)[Joseph]. To #myhl(menColor)[Machir] the firstborn of #myhl(menColor)[Manasseh], the father of #myhl(menColor)[Gilead], were allotted #myhl(placesColor)[Gilead] and #myhl(placesColor)[Bashan], because he was a man of war. 
#versenum(2) And allotments were made to the rest of the people of #myname[Manasseh] by their clans, #myhl(menColor)[Abiezer], #underline[#myhl(menColor)[Helek]], #underline[#myhl(menColor)[Asriel]], #myhl(menColor)[Shechem], #myhl(menColor)[Hepher], and #underline[#myhl(menColor)[Shemida]]. These were the male descendants of #myhl(menColor)[Manasseh] the son of #myhl(menColor)[Joseph], by their clans.


  
#versenum(3) Now #underline[#myhl(menColor)[Zelophehad]] the son of #myhl(menColor)[Hepher], son of #myhl(menColor)[Gilead], son of #myhl(menColor)[Machir], son of #myhl(menColor)[Manasseh], had no sons, but only daughters, and these are the names of his daughters: #underline[#myhl(womenColor)[Mahlah]], #underline[#myhl(womenColor)[Noah]], #underline[#myhl(womenColor)[Hoglah]], #underline[#myhl(womenColor)[Milcah]], and #myhl(womenColor)[Tirzah]. 
#versenum(4) They #underline[approached] #myhl(menColor)[Eleazar] the priest and #myhl(menColor)[Joshua] the son of #myhl(menColor)[Nun] and the leaders and said, “The #myhl(divineColor)[#smallcaps[Lord]] commanded #myhl(menColor)[Moses] to give us an inheritance along with our brothers.” So according to the mouth of the #myhl(divineColor)[#smallcaps[Lord]] he gave them an inheritance among the brothers of their father. 
#versenum(5) Thus there fell to #myname[Manasseh] #mynumber[ten] portions, besides the land of #myhl(placesColor)[Gilead] and #myhl(placesColor)[Bashan], which is on the other side of the #myhl(placesColor)[Jordan], 
#versenum(6) because the daughters of #myname[Manasseh] received an inheritance along with his sons. The land of #myhl(placesColor)[Gilead] was allotted to the rest of the people of #myname[Manasseh].


  
#versenum(7) The territory of #myhl(placesColor)[Manasseh] reached from #myhl(placesColor)[Asher] to #myhl(placesColor)[Michmethath], which is east of #myhl(placesColor)[Shechem]. Then the boundary goes along southward to the inhabitants of #underline[#myhl(placesColor)[En-tappuah]]. 
#versenum(8) The land of #myhl(placesColor)[Tappuah] belonged to #myname[Manasseh], but the town of #myhl(placesColor)[Tappuah] on the boundary of #myhl(placesColor)[Manasseh] belonged to the people of #myname[Ephraim]. 
#versenum(9) Then the boundary went down to the brook #myhl(placesColor)[Kanah]. These cities, to the south of the brook, among the cities of #myhl(placesColor)[Manasseh], belong to #myname[Ephraim]. Then the boundary of #myhl(placesColor)[Manasseh] goes on the north side of the brook and ends at the sea, 
#versenum(10) the land to the south being #underline[#myname[Ephraim]’s] and that to the north being #underline[#myname[Manasseh]’s], with the sea #underline[forming] its boundary. On the north #myhl(placesColor)[Asher] is reached, and on the east #myhl(placesColor)[Issachar]. 
#versenum(11) Also in #myhl(placesColor)[Issachar] and in #myhl(placesColor)[Asher] #myname[Manasseh] had #myhl(placesColor)[Beth-shean] and its villages, and #myhl(placesColor)[Ibleam] and its villages, and the inhabitants of #myhl(placesColor)[Dor] and its villages, and the inhabitants of #underline[#myhl(placesColor)[En-dor]] and its villages, and the inhabitants of #myhl(placesColor)[Taanach] and its villages, and the inhabitants of #myhl(placesColor)[Megiddo] and its villages; the #mynumber[third] is #underline[#myhl(placesColor)[Naphath]].#footnote[Joshua 17:11 The meaning of the Hebrew is uncertain] 
#versenum(12) Yet the people of #myname[Manasseh] could not take possession of those cities, but the #myname[Canaanites] persisted in dwelling in that land. 
#versenum(13) Now when the people of #myname[Israel] grew strong, they put the #myname[Canaanites] to forced labor, but did not utterly drive them out.


  
#versenum(14) Then the people of #myname[Joseph] spoke to #myhl(menColor)[Joshua], saying, “Why have you given me but one lot and one portion as an inheritance, although I am a numerous people, since all along the #myhl(divineColor)[#smallcaps[Lord]] has blessed me?” 
#versenum(15) And #myhl(menColor)[Joshua] said to them, “If you are a numerous people, go up by yourselves to the forest, and there clear ground for yourselves in the land of the #myname[Perizzites] and the #myname[Rephaim], since #myhl(placesColor)[the hill country of Ephraim] is too #underline[narrow] for you.” 
#versenum(16) The people of #myname[Joseph] said, “The hill country is not enough for us. Yet all the #myname[Canaanites] who dwell in the plain have chariots of iron, both those in #myhl(placesColor)[Beth-shean] and its villages and those in the #myhl(placesColor)[Valley of Jezreel].” 
#versenum(17) Then #myhl(menColor)[Joshua] said to the house of #myname[Joseph], to #myname[Ephraim] and #myname[Manasseh], “You are a numerous people and have great power. You shall not have one allotment only, 
#versenum(18) but the hill country shall be yours, for though it is a forest, you shall clear it and possess it to its farthest #underline[borders]. For you shall drive out the #myname[Canaanites], though they have chariots of iron, and though they are strong.”


  
#chapter-heading[Joshua 18]


#section-heading[Allotment of the Remaining Land]


#versenum(1) Then the whole congregation of the people of #myname[Israel] assembled at #myhl(placesColor)[Shiloh] and set up the tent of meeting there. The land lay subdued before them.


  
#versenum(2) There remained among the people of #myname[Israel] #mynumber[seven] tribes whose inheritance had not yet been apportioned. 
#versenum(3) So #myhl(menColor)[Joshua] said to the people of #myname[Israel], “How long will you put off going in to take possession of the land, which the #myhl(divineColor)[#smallcaps[Lord], the God] of your fathers, has given you? 
#versenum(4) #underline[Provide] #mynumber[three] men from each tribe, and I will send them out that they may set out and go up and down the land. They shall write a description of it with a view to their inheritances, and then come to me. 
#versenum(5) They shall divide it into #mynumber[seven] portions. #myname[Judah] shall continue in his territory on the south, and the house of #myname[Joseph] shall continue in their territory on the north. 
#versenum(6) And you shall #underline[describe] the land in #mynumber[seven] divisions and bring the description here to me. And I will cast lots for you here before the #myhl(divineColor)[#smallcaps[Lord]] our #myhl(divineColor)[God]. 
#versenum(7) The #myname[Levites] have no portion among you, for the #underline[priesthood] of the #myhl(divineColor)[#smallcaps[Lord]] is their #underline[heritage]. And #myname[Gad] and #myname[Reuben] and #mynumber[half] the tribe of #myname[Manasseh] have received their inheritance beyond the #myhl(placesColor)[Jordan] eastward, which #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] gave them.”


  
#versenum(8) So the men arose and went, and #myhl(menColor)[Joshua] charged those who went to write the description of the land, saying, “Go up and down in the land and write a description and return to me. And I will cast lots for you here before the #myhl(divineColor)[#smallcaps[Lord]] in #myhl(placesColor)[Shiloh].” 
#versenum(9) So the men went and passed up and down in the land and wrote in a book a description of it by towns in #mynumber[seven] divisions. Then they came to #myhl(menColor)[Joshua] to the camp at #myhl(placesColor)[Shiloh], 
#versenum(10) and #myhl(menColor)[Joshua] cast lots for them in #myhl(placesColor)[Shiloh] before the #myhl(divineColor)[#smallcaps[Lord]]. And there #myhl(menColor)[Joshua] apportioned the land to the people of #myname[Israel], to each his portion.


  
#section-heading[The Inheritance for Benjamin]


#versenum(11) The lot of the tribe of the people of #myname[Benjamin] according to its clans came up, and the territory allotted to it fell between the people of #myname[Judah] and the people of #myname[Joseph]. 
#versenum(12) On the north side their boundary began at the #myhl(placesColor)[Jordan]. Then the boundary goes up to the shoulder north of #myhl(placesColor)[Jericho], then up through the hill country westward, and it ends at the wilderness of #myhl(placesColor)[Beth-aven]. 
#versenum(13) From there the boundary passes along southward in the direction of #myhl(placesColor)[Luz], to the shoulder of #myhl(placesColor)[Luz] (that is, #myhl(placesColor)[Bethel]), then the boundary goes down to #myhl(placesColor)[Ataroth-addar], on the mountain that lies south of #myhl(placesColor)[Lower Beth-horon]. 
#versenum(14) Then the boundary goes in another direction, turning on the western side southward from the mountain that lies to the south, opposite #myhl(placesColor)[Beth-horon], and it ends at #myhl(placesColor)[Kiriath-baal] (that is, #myhl(placesColor)[Kiriath-jearim]), a city belonging to the people of #myname[Judah]. This forms the western side. 
#versenum(15) And the southern side #underline[begins] at the outskirts of #myhl(placesColor)[Kiriath-jearim]. And the boundary goes from there to #myhl(placesColor)[Ephron],#footnote[Joshua 18:15 See 15:9; Hebrew #emph[westward]] to the spring of the waters of #myhl(placesColor)[Nephtoah]. 
#versenum(16) Then the boundary goes down to the border of the mountain that #underline[overlooks] the #myhl(placesColor)[Valley of the Son of Hinnom], which is at the north end of the #myhl(placesColor)[Valley of Rephaim]. And it then goes down the #myhl(placesColor)[Valley of Hinnom], south of the shoulder of the #myname[Jebusites], and #underline[downward] to #myhl(placesColor)[En-rogel]. 
#versenum(17) Then it bends in a #underline[northerly] direction going on to #myhl(placesColor)[En-shemesh], and from there goes to #underline[#myhl(placesColor)[Geliloth]], which is opposite the ascent of #myhl(placesColor)[Adummim]. Then it goes down to the stone of #myhl(menColor)[Bohan] the son of #myhl(menColor)[Reuben], 
#versenum(18) and passing on to the north of the shoulder of #myhl(placesColor)[Beth-arabah]#footnote[Joshua 18:18 Septuagint; Hebrew #emph[to the shoulder over against the Arabah]] it goes down to the #myhl(placesColor)[Arabah]. 
#versenum(19) Then the boundary passes on to the north of the shoulder of #myhl(placesColor)[Beth-hoglah]. And the boundary ends at the northern bay of the #myhl(placesColor)[Salt Sea], at the south end of the #myhl(placesColor)[Jordan]: this is the southern border. 
#versenum(20) The #myhl(placesColor)[Jordan] forms its boundary on the #underline[eastern] side. This is the inheritance of the people of #myname[Benjamin], according to their clans, boundary by boundary all around.


  
#versenum(21) Now the cities of the tribe of the people of #myname[Benjamin] according to their clans were #myhl(placesColor)[Jericho], #myhl(placesColor)[Beth-hoglah], #underline[#myhl(placesColor)[Emek-keziz]], 
#versenum(22) #myhl(placesColor)[Beth-arabah], #underline[#myhl(placesColor)[Zemaraim]], #myhl(placesColor)[Bethel], 
#versenum(23) #myhl(placesColor)[Avvim], #underline[#myhl(placesColor)[Parah]], #myhl(placesColor)[Ophrah], 
#versenum(24) #underline[#myhl(placesColor)[Chephar-ammoni]], #underline[#myhl(placesColor)[Ophni]], #myhl(placesColor)[Geba]—#mynumber[twelve] cities with their villages: 
#versenum(25) #myhl(placesColor)[Gibeon], #myhl(placesColor)[Ramah], #myhl(placesColor)[Beeroth], 
#versenum(26) #myhl(placesColor)[Mizpeh], #myhl(placesColor)[Chephirah], #underline[#myhl(placesColor)[Mozah]], 
#versenum(27) #myhl(placesColor)[Rekem], #underline[#myhl(placesColor)[Irpeel]], #underline[#myhl(placesColor)[Taralah]], 
#versenum(28) #underline[#myhl(placesColor)[Zela]], #underline[#myhl(placesColor)[Haeleph]], #myhl(placesColor)[Jebus]#footnote[Joshua 18:28 Septuagint, Syriac, Vulgate; Hebrew #emph[the Jebusite]] (that is, #myhl(placesColor)[Jerusalem]), #myhl(placesColor)[Gibeah]#footnote[Joshua 18:28 Hebrew #emph[Gibeath]] and #myhl(placesColor)[Kiriath-jearim]#footnote[Joshua 18:28 Septuagint; Hebrew #emph[Kiriath]]—#mynumber[fourteen] cities with their villages. This is the inheritance of the people of #myname[Benjamin] according to its clans.


  
#chapter-heading[Joshua 19]


#section-heading[The Inheritance for Simeon]


#versenum(1) The #mynumber[second] lot came out for #myname[Simeon], for the tribe of the people of #myname[Simeon], according to their clans, and their inheritance was in the midst of the inheritance of the people of #myname[Judah]. 
#versenum(2) And they had for their inheritance #myhl(placesColor)[Beersheba], #underline[#myhl(placesColor)[Sheba]], #myhl(placesColor)[Moladah], 
#versenum(3) #myhl(placesColor)[Hazar-shual], #underline[#myhl(placesColor)[Balah]], #myhl(placesColor)[Ezem], 
#versenum(4) #myhl(placesColor)[Eltolad], #underline[#myhl(placesColor)[Bethul]], #myhl(placesColor)[Hormah], 
#versenum(5) #myhl(placesColor)[Ziklag], #underline[#myhl(placesColor)[Beth-marcaboth]], #underline[#myhl(placesColor)[Hazar-susah]], 
#versenum(6) #underline[#myhl(placesColor)[Beth-lebaoth]], and #underline[#myhl(placesColor)[Sharuhen]]—#mynumber[thirteen] cities with their villages; 
#versenum(7) #myhl(placesColor)[Ain], #myhl(placesColor)[Rimmon], #myhl(placesColor)[Ether], and #myhl(placesColor)[Ashan]—#mynumber[four] cities with their villages, 
#versenum(8) together with all the villages around these cities as far as #underline[#myhl(placesColor)[Baalath-beer]], #myhl(placesColor)[Ramah] of the #myhl(placesColor)[Negeb]. This was the inheritance of the tribe of the people of #myname[Simeon] according to their clans. 
#versenum(9) The inheritance of the people of #myname[Simeon] formed part of the territory of the people of #myname[Judah]. Because the portion of the people of #myname[Judah] was too large for them, the people of #myname[Simeon] #underline[obtained] an inheritance in the midst of their inheritance.


  
#section-heading[The Inheritance for Zebulun]


#versenum(10) The #mynumber[third] lot came up for the people of #myname[Zebulun], according to their clans. And the territory of their inheritance reached as far as #myhl(placesColor)[Sarid]. 
#versenum(11) Then their boundary goes up westward and on to #underline[#myhl(placesColor)[Mareal]] and touches #underline[#myhl(placesColor)[Dabbesheth]], then the brook that is east of #myhl(placesColor)[Jokneam]. 
#versenum(12) From #myhl(placesColor)[Sarid] it goes in the other direction eastward toward the sunrise to the boundary of #underline[#myhl(placesColor)[Chisloth-tabor]]. From there it goes to #myhl(placesColor)[Daberath], then up to #myhl(placesColor)[Japhia]. 
#versenum(13) From there it passes along on the east toward the sunrise to #underline[#myhl(placesColor)[Gath-hepher]], to #underline[#myhl(placesColor)[Eth-kazin]], and going on to #myhl(placesColor)[Rimmon] it bends toward #underline[#myhl(placesColor)[Neah]], 
#versenum(14) then on the north the boundary turns about to #underline[#myhl(placesColor)[Hannathon]], and it ends at the #myhl(placesColor)[Valley of Iphtahel]; 
#versenum(15) and #underline[#myhl(placesColor)[Kattath]], #myhl(placesColor)[Nahalal], #myhl(placesColor)[Shimron], #underline[#myhl(placesColor)[Idalah]], and #myhl(placesColor)[Bethlehem]—#mynumber[twelve] cities with their villages. 
#versenum(16) This is the inheritance of the people of #myname[Zebulun], according to their clans—these cities with their villages.


  
#section-heading[The Inheritance for Issachar]


#versenum(17) The #mynumber[fourth] lot came out for #myname[Issachar], for the people of #myname[Issachar], according to their clans. 
#versenum(18) Their territory included #myhl(placesColor)[Jezreel], #underline[#myhl(placesColor)[Chesulloth]], #underline[#myhl(placesColor)[Shunem]], 
#versenum(19) #underline[#myhl(placesColor)[Hapharaim]], #underline[#myhl(placesColor)[Shion]], #underline[#myhl(placesColor)[Anaharath]], 
#versenum(20) #underline[#myhl(placesColor)[Rabbith]], #myhl(placesColor)[Kishion], #underline[#myhl(placesColor)[Ebez]], 
#versenum(21) #underline[#myhl(placesColor)[Remeth]], #myhl(placesColor)[En-gannim], #underline[#myhl(placesColor)[En-haddah]], #underline[#myhl(placesColor)[Beth-pazzez]]. 
#versenum(22) The boundary also touches #myhl(placesColor)[Tabor], #underline[#myhl(placesColor)[Shahazumah]], and #myhl(placesColor)[Beth-shemesh], and its boundary ends at the #myhl(placesColor)[Jordan]—#mynumber[sixteen] cities with their villages. 
#versenum(23) This is the inheritance of the tribe of the people of #myname[Issachar], according to their clans—the cities with their villages.


  
#section-heading[The Inheritance for Asher]


#versenum(24) The #mynumber[fifth] lot came out for the tribe of the people of #myname[Asher] according to their clans. 
#versenum(25) Their territory included #myhl(placesColor)[Helkath], #underline[#myhl(placesColor)[Hali]], #underline[#myhl(placesColor)[Beten]], #myhl(placesColor)[Achshaph], 
#versenum(26) #underline[#myhl(placesColor)[Allammelech]], #underline[#myhl(placesColor)[Amad]], and #myhl(placesColor)[Mishal]. On the west it touches #myhl(placesColor)[Carmel] and #underline[#myhl(placesColor)[Shihor-libnath]], 
#versenum(27) then it turns eastward, it goes to #myhl(placesColor)[Beth-dagon], and touches #myname[Zebulun] and the #myhl(placesColor)[Valley of Iphtahel] northward to #underline[#myhl(placesColor)[Beth-emek]] and #underline[#myhl(placesColor)[Neiel]]. Then it #underline[continues] in the north to #underline[#myhl(placesColor)[Cabul]], 
#versenum(28) #underline[#myhl(placesColor)[Ebron]], #myhl(placesColor)[Rehob], #underline[#myhl(placesColor)[Hammon]], #myhl(placesColor)[Kanah], as far as #myhl(placesColor)[Sidon] the Great. 
#versenum(29) Then the boundary turns to #myhl(placesColor)[Ramah], #underline[reaching] to the fortified city of #underline[#myhl(placesColor)[Tyre]]. Then the boundary turns to #underline[#myhl(placesColor)[Hosah]], and it ends at the sea; #underline[#myhl(placesColor)[Mahalab]],#footnote[Joshua 19:29 Compare Septuagint; Hebrew #emph[Mehebel]] #myhl(placesColor)[Achzib], 
#versenum(30) #underline[#myhl(placesColor)[Ummah]], #myhl(placesColor)[Aphek] and #myhl(placesColor)[Rehob]—#mynumber[twenty-two] cities with their villages. 
#versenum(31) This is the inheritance of the tribe of the people of #myname[Asher] according to their clans—these cities with their villages.


  
#section-heading[The Inheritance for Naphtali]


#versenum(32) The #underline[#mynumber[sixth]] lot came out for the people of #myname[Naphtali], for the people of #myname[Naphtali], according to their clans. 
#versenum(33) And their boundary ran from #underline[#myhl(placesColor)[Heleph]], from the oak in #myhl(placesColor)[Zaanannim], and #underline[#myhl(placesColor)[Adami-nekeb]], and #myhl(placesColor)[Jabneel], as far as #underline[#myhl(placesColor)[Lakkum]], and it #underline[ended] at the #myhl(placesColor)[Jordan]. 
#versenum(34) Then the boundary turns westward to #underline[#myhl(placesColor)[Aznoth-tabor]] and goes from there to #underline[#myhl(placesColor)[Hukkok]], #underline[touching] #myname[Zebulun] at the south and #myname[Asher] on the west and #myname[Judah] on the east at the #myhl(placesColor)[Jordan]. 
#versenum(35) The fortified cities are #underline[#myhl(placesColor)[Ziddim]], #underline[#myhl(placesColor)[Zer]], #underline[#myhl(placesColor)[Hammath]], #underline[#myhl(placesColor)[Rakkath]], #myhl(placesColor)[Chinnereth], 
#versenum(36) #underline[#myhl(placesColor)[Adamah]], #myhl(placesColor)[Ramah], #myhl(placesColor)[Hazor], 
#versenum(37) #myhl(placesColor)[Kedesh], #myhl(placesColor)[Edrei], #underline[#myhl(placesColor)[En-hazor]], 
#versenum(38) #underline[#myhl(placesColor)[Yiron]], #underline[#myhl(placesColor)[Migdal-el]], #underline[#myhl(placesColor)[Horem]], #myhl(placesColor)[Beth-anath], and #myhl(placesColor)[Beth-shemesh]—#underline[#mynumber[nineteen]] cities with their villages. 
#versenum(39) This is the inheritance of the tribe of the people of #myname[Naphtali] according to their clans—the cities with their villages.


  
#section-heading[The Inheritance for Dan]


#versenum(40) The #mynumber[seventh] lot came out for the tribe of the people of #myname[Dan], according to their clans. 
#versenum(41) And the territory of its inheritance included #myhl(placesColor)[Zorah], #myhl(placesColor)[Eshtaol], #underline[#myhl(placesColor)[Ir-shemesh]], 
#versenum(42) #underline[#myhl(placesColor)[Shaalabbin]], #myhl(placesColor)[Aijalon], #underline[#myhl(placesColor)[Ithlah]], 
#versenum(43) #myhl(placesColor)[Elon], #myhl(placesColor)[Timnah], #myhl(placesColor)[Ekron], 
#versenum(44) #underline[#myhl(placesColor)[Eltekeh]], #myhl(placesColor)[Gibbethon], #underline[#myhl(placesColor)[Baalath]], 
#versenum(45) #underline[#myhl(placesColor)[Jehud]], #underline[#myhl(placesColor)[Bene-berak]], #myhl(placesColor)[Gath-rimmon], 
#versenum(46) and #underline[#myhl(placesColor)[Me-jarkon]] and #underline[#myhl(placesColor)[Rakkon]] with the territory over against #underline[#myhl(placesColor)[Joppa]]. 
#versenum(47) When the territory of the people of #myname[Dan] was #underline[lost] to them, the people of #myname[Dan] went up and fought against #myhl(placesColor)[Leshem], and after #underline[capturing] it and striking it with the sword they took possession of it and settled in it, #underline[calling] #myhl(placesColor)[Leshem], #myname[Dan], after the name of #myname[Dan] their ancestor. 
#versenum(48) This is the inheritance of the tribe of the people of #myname[Dan], according to their clans—these cities with their villages.


  
#section-heading[The Inheritance for Joshua]


#versenum(49) When they had finished #underline[distributing] the several #underline[territories] of the land as inheritances, the people of #myname[Israel] gave an inheritance among them to #myhl(menColor)[Joshua] the son of #myhl(menColor)[Nun]. 
#versenum(50) By command of the #myhl(divineColor)[#smallcaps[Lord]] they gave him the city that he asked, #myhl(placesColor)[Timnath-serah] in #myhl(placesColor)[the hill country of Ephraim]. And he rebuilt the city and settled in it.


  
#versenum(51) These are the inheritances that #myhl(menColor)[Eleazar] the priest and #myhl(menColor)[Joshua] the son of #myhl(menColor)[Nun] and the heads of the fathers’ houses of the tribes of the people of #myname[Israel] distributed by lot at #myhl(placesColor)[Shiloh] before the #myhl(divineColor)[#smallcaps[Lord]], at the entrance of the tent of meeting. So they finished #underline[dividing] the land.


  
#chapter-heading[Joshua 20]


#section-heading[The Cities of Refuge]


#versenum(1) Then the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Joshua], 
#versenum(2) “Say to the people of #myname[Israel], ‘#underline[Appoint] the cities of refuge, of which I spoke to you through #myhl(menColor)[Moses], 
#versenum(3) that the manslayer who #underline[strikes] any person without intent or unknowingly may flee there. They shall be for you a refuge from the avenger of blood. 
#versenum(4) He shall flee to one of these cities and shall stand at the entrance of the gate of the city and #underline[explain] his #underline[case] to the elders of that city. Then they shall take him into the city and give him a place, and he shall remain with them. 
#versenum(5) And if the avenger of blood #underline[pursues] him, they shall not give up the manslayer into his hand, because he struck his #underline[neighbor] unknowingly, and did not hate him in the #underline[past]. 
#versenum(6) And he shall remain in that city until he has stood before the congregation for judgment, until the death of him who is #underline[high] priest at the time. Then the manslayer may return to his own town and his own home, to the town from which he fled.’”


  
#versenum(7) So they set apart #myhl(placesColor)[Kedesh] in #myhl(placesColor)[Galilee] in #myhl(placesColor)[the hill country of Naphtali], and #myhl(placesColor)[Shechem] in #myhl(placesColor)[the hill country of Ephraim], and #myhl(placesColor)[Kiriath-arba] (that is, #myhl(placesColor)[Hebron]) in #myhl(placesColor)[the hill country of Judah]. 
#versenum(8) And beyond the #myhl(placesColor)[Jordan] east of #myhl(placesColor)[Jericho], they appointed #myhl(placesColor)[Bezer] in the wilderness on the tableland, from the tribe of #myname[Reuben], and #myhl(placesColor)[Ramoth] in #myhl(placesColor)[Gilead], from the tribe of #myname[Gad], and #myhl(placesColor)[Golan] in #myhl(placesColor)[Bashan], from the tribe of #myname[Manasseh]. 
#versenum(9) These were the cities #underline[designated] for all the people of #myname[Israel] and for the #underline[stranger] sojourning among them, that anyone who killed a person without intent could flee there, so that he might not die by the hand of the avenger of blood, till he stood before the congregation.


  
#chapter-heading[Joshua 21]


#section-heading[Cities and Pasturelands Allotted to Levi]


#versenum(1) Then the heads of the fathers’ houses of the #myname[Levites] came to #myhl(menColor)[Eleazar] the priest and to #myhl(menColor)[Joshua] the son of #myhl(menColor)[Nun] and to the heads of the fathers’ houses of the tribes of the people of #myname[Israel]. 
#versenum(2) And they said to them at #myhl(placesColor)[Shiloh] in the land of #myhl(placesColor)[Canaan], “The #myhl(divineColor)[#smallcaps[Lord]] commanded through #myhl(menColor)[Moses] that we be given cities to dwell in, along with their pasturelands for our livestock.” 
#versenum(3) So by command of the #myhl(divineColor)[#smallcaps[Lord]] the people of #myname[Israel] gave to the #myname[Levites] the following cities and pasturelands out of their inheritance.


  
#versenum(4) The lot came out for the clans of the #myname[Kohathites]. So those #myname[Levites] who were descendants of #myhl(menColor)[Aaron] the priest received by lot from the tribes of #myname[Judah], #myname[Simeon], and #myname[Benjamin], #mynumber[thirteen] cities.


  
#versenum(5) And the rest of the #myname[Kohathites] received by lot from the clans of the tribe of #myname[Ephraim], from the tribe of #myname[Dan] and the #mynumber[half]-tribe of #myname[Manasseh], #mynumber[ten] cities.


  
#versenum(6) The #myname[Gershonites] received by lot from the clans of the tribe of #myname[Issachar], from the tribe of #myname[Asher], from the tribe of #myname[Naphtali], and from the #mynumber[half]-tribe of #myname[Manasseh] in #myhl(placesColor)[Bashan], #mynumber[thirteen] cities.


  
#versenum(7) The #underline[#myname[Merarites]] according to their clans received from the tribe of #myname[Reuben], the tribe of #myname[Gad], and the tribe of #myname[Zebulun], #mynumber[twelve] cities.


  
#versenum(8) These cities and their pasturelands the people of #myname[Israel] gave by lot to the #myname[Levites], as the #myhl(divineColor)[#smallcaps[Lord]] had commanded through #myhl(menColor)[Moses].


  
#versenum(9) Out of the tribe of the people of #myname[Judah] and the tribe of the people of #myname[Simeon] they gave the following cities #underline[mentioned] by name, 
#versenum(10) which went to the descendants of #myhl(menColor)[Aaron], one of the clans of the #myname[Kohathites] who belonged to the people of #myname[Levi]; since the lot fell to them #mynumber[first]. 
#versenum(11) They gave them #myhl(placesColor)[Kiriath-arba] (#myhl(menColor)[Arba] being the father of #myhl(menColor)[Anak]), that is #myhl(placesColor)[Hebron], in #myhl(placesColor)[the hill country of Judah], along with the pasturelands around it. 
#versenum(12) But the fields of the city and its villages had been given to #myhl(menColor)[Caleb] the son of #myhl(menColor)[Jephunneh] as his possession.


  
#versenum(13) And to the descendants of #myhl(menColor)[Aaron] the priest they gave #myhl(placesColor)[Hebron], the city of refuge for the manslayer, with its pasturelands, #myhl(placesColor)[Libnah] with its pasturelands, 
#versenum(14) #myhl(placesColor)[Jattir] with its pasturelands, #underline[#myhl(placesColor)[Eshtemoa]] with its pasturelands, 
#versenum(15) #myhl(placesColor)[Holon] with its pasturelands, #myhl(placesColor)[Debir] with its pasturelands, 
#versenum(16) #myhl(placesColor)[Ain] with its pasturelands, #myhl(placesColor)[Juttah] with its pasturelands, #myhl(placesColor)[Beth-shemesh] with its pasturelands—#mynumber[nine] cities out of these #mynumber[two] tribes; 
#versenum(17) then out of the tribe of #myname[Benjamin], #myhl(placesColor)[Gibeon] with its pasturelands, #myhl(placesColor)[Geba] with its pasturelands, 
#versenum(18) #underline[#myhl(placesColor)[Anathoth]] with its pasturelands, and #underline[#myhl(placesColor)[Almon]] with its pasturelands—#mynumber[four] cities. 
#versenum(19) The cities of the descendants of #myhl(menColor)[Aaron], the priests, were in all #mynumber[thirteen] cities with their pasturelands.


  
#versenum(20) As to the rest of the #myname[Kohathites] belonging to the #underline[#myname[Kohathite]] clans of the #myname[Levites], the cities allotted to them were out of the tribe of #myname[Ephraim]. 
#versenum(21) To them were given #myhl(placesColor)[Shechem], the city of refuge for the manslayer, with its pasturelands in #myhl(placesColor)[the hill country of Ephraim], #myhl(placesColor)[Gezer] with its pasturelands, 
#versenum(22) #underline[#myname[Kibzaim]] with its pasturelands, #myhl(placesColor)[Beth-horon] with its pasturelands—#mynumber[four] cities; 
#versenum(23) and out of the tribe of #myname[Dan], #underline[#myhl(placesColor)[Elteke]] with its pasturelands, #myhl(placesColor)[Gibbethon] with its pasturelands, 
#versenum(24) #myhl(placesColor)[Aijalon] with its pasturelands, #myhl(placesColor)[Gath-rimmon] with its pasturelands—#mynumber[four] cities; 
#versenum(25) and out of the #mynumber[half]-tribe of #myname[Manasseh], #myhl(placesColor)[Taanach] with its pasturelands, and #myhl(placesColor)[Gath-rimmon] with its pasturelands—#mynumber[two] cities. 
#versenum(26) The cities of the clans of the rest of the #myname[Kohathites] were #mynumber[ten] in all with their pasturelands.


  
#versenum(27) And to the #myname[Gershonites], one of the clans of the #myname[Levites], were given out of the #mynumber[half]-tribe of #myname[Manasseh], #myhl(placesColor)[Golan] in #myhl(placesColor)[Bashan] with its pasturelands, the city of refuge for the manslayer, and #underline[#myhl(placesColor)[Beeshterah]] with its pasturelands—#mynumber[two] cities; 
#versenum(28) and out of the tribe of #myname[Issachar], #myhl(placesColor)[Kishion] with its pasturelands, #myhl(placesColor)[Daberath] with its pasturelands, 
#versenum(29) #myhl(placesColor)[Jarmuth] with its pasturelands, #myhl(placesColor)[En-gannim] with its pasturelands—#mynumber[four] cities; 
#versenum(30) and out of the tribe of #myname[Asher], #myhl(placesColor)[Mishal] with its pasturelands, #myhl(menColor)[Abdon] with its pasturelands, 
#versenum(31) #myhl(placesColor)[Helkath] with its pasturelands, and #myhl(placesColor)[Rehob] with its pasturelands—#mynumber[four] cities; 
#versenum(32) and out of the tribe of #myname[Naphtali], #myhl(placesColor)[Kedesh] in #myhl(placesColor)[Galilee] with its pasturelands, the city of refuge for the manslayer, #underline[#myhl(placesColor)[Hammoth-dor]] with its pasturelands, and #underline[#myhl(placesColor)[Kartan]] with its pasturelands—#mynumber[three] cities. 
#versenum(33) The cities of the several clans of the #myname[Gershonites] were in all #mynumber[thirteen] cities with their pasturelands.


  
#versenum(34) And to the rest of the #myname[Levites], the #myname[Merarite] clans, were given out of the tribe of #myname[Zebulun], #myhl(placesColor)[Jokneam] with its pasturelands, #underline[#myhl(placesColor)[Kartah]] with its pasturelands, 
#versenum(35) #underline[#myhl(placesColor)[Dimnah]] with its pasturelands, #myhl(placesColor)[Nahalal] with its pasturelands—#mynumber[four] cities; 
#versenum(36) and out of the tribe of #myname[Reuben], #myhl(placesColor)[Bezer] with its pasturelands, #myhl(placesColor)[Jahaz] with its pasturelands, 
#versenum(37) #myhl(placesColor)[Kedemoth] with its pasturelands, and #myhl(placesColor)[Mephaath] with its pasturelands—#mynumber[four] cities; 
#versenum(38) and out of the tribe of #myname[Gad], #myhl(placesColor)[Ramoth] in #myhl(placesColor)[Gilead] with its pasturelands, the city of refuge for the manslayer, #myhl(placesColor)[Mahanaim] with its pasturelands, 
#versenum(39) #myhl(placesColor)[Heshbon] with its pasturelands, #myhl(placesColor)[Jazer] with its pasturelands—#mynumber[four] cities in all. 
#versenum(40) As for the cities of the several #myname[Merarite] clans, that is, the #underline[remainder] of the clans of the #myname[Levites], those allotted to them were in all #mynumber[twelve] cities.


  
#versenum(41) The cities of the #myname[Levites] in the midst of the possession of the people of #myname[Israel] were in all #underline[#mynumber[forty-eight]] cities with their pasturelands. 
#versenum(42) These cities each had its pasturelands around it. So it was with all these cities.


  
#versenum(43) Thus the #myhl(divineColor)[#smallcaps[Lord]] gave to #myname[Israel] all the land that he swore to give to their fathers. And they took possession of it, and they settled there. 
#versenum(44) And the #myhl(divineColor)[#smallcaps[Lord]] gave them rest on every side just as he had sworn to their fathers. Not one of all their enemies had #underline[withstood] them, for the #myhl(divineColor)[#smallcaps[Lord]] had given all their enemies into their hands. 
#versenum(45) Not one word of all the good #underline[promises] that the #myhl(divineColor)[#smallcaps[Lord]] had made to the house of #myname[Israel] had failed; all came to pass.


  
#chapter-heading[Joshua 22]


#section-heading[The Eastern Tribes Return Home]


#versenum(1) At that time #myhl(menColor)[Joshua] summoned the #myname[Reubenites] and the #myname[Gadites] and the #mynumber[half]-tribe of #myname[Manasseh], 
#versenum(2) and said to them, “You have kept all that #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] commanded you and have obeyed my voice in all that I have commanded you. 
#versenum(3) You have not forsaken your brothers these many days, down to this day, but have been careful to keep the charge of the #myhl(divineColor)[#smallcaps[Lord] your God]. 
#versenum(4) And now the #myhl(divineColor)[#smallcaps[Lord] your God] has given rest to your brothers, as he promised them. Therefore turn and go to your tents in the land where your possession lies, which #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] gave you on the other side of the #myhl(placesColor)[Jordan]. 
#versenum(5) Only be very careful to observe the commandment and the law that #myhl(menColor)[Moses] the servant of the #myhl(divineColor)[#smallcaps[Lord]] commanded you, to love the #myhl(divineColor)[#smallcaps[Lord] your God], and to walk in all his ways and to keep his commandments and to cling to him and to serve him with all your heart and with all your soul.” 
#versenum(6) So #myhl(menColor)[Joshua] blessed them and sent them away, and they went to their tents.


  
#versenum(7) Now to the one #mynumber[half] of the tribe of #myname[Manasseh] #myhl(menColor)[Moses] had given a possession in #myhl(placesColor)[Bashan], but to the other #mynumber[half] #myhl(menColor)[Joshua] had given a possession beside their brothers in the land west of the #myhl(placesColor)[Jordan]. And when #myhl(menColor)[Joshua] sent them away to their #underline[homes] and blessed them, 
#versenum(8) he said to them, “Go back to your tents with much wealth and with very much livestock, with silver, gold, bronze, and iron, and with much #underline[clothing]. Divide the spoil of your enemies with your brothers.” 
#versenum(9) So the people of #myname[Reuben] and the people of #myname[Gad] and the #mynumber[half]-tribe of #myname[Manasseh] returned home, #underline[parting] from the people of #myname[Israel] at #myhl(placesColor)[Shiloh], which is in the land of #myhl(placesColor)[Canaan], to go to the land of #myhl(placesColor)[Gilead], their own land of which they had #underline[possessed] themselves by command of the #myhl(divineColor)[#smallcaps[Lord]] through #myhl(menColor)[Moses].


  
#section-heading[The Eastern Tribes’ Altar of Witness]


#versenum(10) And when they came to the region of the #myhl(placesColor)[Jordan] that is in the land of #myhl(placesColor)[Canaan], the people of #myname[Reuben] and the people of #myname[Gad] and the #mynumber[half]-tribe of #myname[Manasseh] built there an altar by the #myhl(placesColor)[Jordan], an altar of #underline[imposing] #underline[size]. 
#versenum(11) And the people of #myname[Israel] heard it said, “Behold, the people of #myname[Reuben] and the people of #myname[Gad] and the #mynumber[half]-tribe of #myname[Manasseh] have built the altar at the #underline[frontier] of the land of #myhl(placesColor)[Canaan], in the region about the #myhl(placesColor)[Jordan], on the side that belongs to the people of #myname[Israel].” 
#versenum(12) And when the people of #myname[Israel] heard of it, the whole assembly of the people of #myname[Israel] gathered at #myhl(placesColor)[Shiloh] to make war against them.


  
#versenum(13) Then the people of #myname[Israel] sent to the people of #myname[Reuben] and the people of #myname[Gad] and the #mynumber[half]-tribe of #myname[Manasseh], in the land of #myhl(placesColor)[Gilead], #myhl(menColor)[Phinehas] the son of #myhl(menColor)[Eleazar] the priest, 
#versenum(14) and with him #mynumber[ten] chiefs, one from each of the tribal families of #myname[Israel], every one of them the head of a family among the clans of #myname[Israel]. 
#versenum(15) And they came to the people of #myname[Reuben], the people of #myname[Gad], and the #mynumber[half]-tribe of #myname[Manasseh], in the land of #myhl(placesColor)[Gilead], and they said to them, 
#versenum(16) “Thus says the whole congregation of the #myhl(divineColor)[#smallcaps[Lord]], ‘What is this breach of faith that you have committed against the #myhl(divineColor)[God of Israel] in turning away this day from following the #myhl(divineColor)[#smallcaps[Lord]] by building yourselves an altar this day in rebellion against the #myhl(divineColor)[#smallcaps[Lord]]? 
#versenum(17) Have we not had enough of the #underline[sin] at #underline[#myhl(placesColor)[Peor]] from which even yet we have not #underline[cleansed] #underline[ourselves], and for which there came a #underline[plague] upon the congregation of the #myhl(divineColor)[#smallcaps[Lord]], 
#versenum(18) that you too must turn away this day from following the #myhl(divineColor)[#smallcaps[Lord]]? And if you too rebel against the #myhl(divineColor)[#smallcaps[Lord]] today then tomorrow he will be angry with the whole congregation of #myname[Israel]. 
#versenum(19) But now, if the land of your possession is unclean, pass over into the #myhl(divineColor)[#smallcaps[Lord]]’s land where the #myhl(divineColor)[#smallcaps[Lord]]’s tabernacle stands, and take for yourselves a possession among us. Only do not rebel against the #myhl(divineColor)[#smallcaps[Lord]] or make us as rebels by building for yourselves an altar other than the altar of the #myhl(divineColor)[#smallcaps[Lord]] our #myhl(divineColor)[God]. 
#versenum(20) Did not #myhl(menColor)[Achan] the son of #myhl(menColor)[Zerah] break faith in the matter of the devoted things, and wrath fell upon all the congregation of #myname[Israel]? And he did not perish alone for his #underline[iniquity].’”


  
#versenum(21) Then the people of #myname[Reuben], the people of #myname[Gad], and the #mynumber[half]-tribe of #myname[Manasseh] said in answer to the heads of the families of #myname[Israel], 
#versenum(22) “The Mighty One, #myhl(divineColor)[God], the #myhl(divineColor)[#smallcaps[Lord]]! The Mighty One, #myhl(divineColor)[God], the #myhl(divineColor)[#smallcaps[Lord]]! He #underline[knows]; and let #myname[Israel] itself know! If it was in rebellion or in breach of faith against the #myhl(divineColor)[#smallcaps[Lord]], do not #underline[spare] us today 
#versenum(23) for building an altar to turn away from following the #myhl(divineColor)[#smallcaps[Lord]]. Or if we did so to offer burnt offerings or grain offerings or peace offerings on it, may the #myhl(divineColor)[#smallcaps[Lord]] himself take vengeance. 
#versenum(24) No, but we did it from fear that in time to come your children might say to our children, ‘What have you to do with the #myhl(divineColor)[#smallcaps[Lord], the God of Israel]? 
#versenum(25) For the #myhl(divineColor)[#smallcaps[Lord]] has made the #myhl(placesColor)[Jordan] a boundary between us and you, you people of #myname[Reuben] and people of #myname[Gad]. You have no portion in the #myhl(divineColor)[#smallcaps[Lord]].’ So your children might make our children cease to #underline[worship] the #myhl(divineColor)[#smallcaps[Lord]]. 
#versenum(26) Therefore we said, ‘Let us now build an altar, not for burnt offering, nor for sacrifice, 
#versenum(27) but to be a witness between us and you, and between our generations after us, that we do #underline[perform] the #underline[service] of the #myhl(divineColor)[#smallcaps[Lord]] in his presence with our burnt offerings and #underline[sacrifices] and peace offerings, so your children will not say to our children in time to come, “You have no portion in the #myhl(divineColor)[#smallcaps[Lord]].”’ 
#versenum(28) And we thought, ‘If this should be said to us or to our descendants in time to come, we should say, “Behold, the copy of the altar of the #myhl(divineColor)[#smallcaps[Lord]], which our fathers made, not for burnt offerings, nor for sacrifice, but to be a witness between us and you.”’ 
#versenum(29) Far be it from us that we should rebel against the #myhl(divineColor)[#smallcaps[Lord]] and turn away this day from following the #myhl(divineColor)[#smallcaps[Lord]] by building an altar for burnt offering, grain offering, or sacrifice, other than the altar of the #myhl(divineColor)[#smallcaps[Lord]] our #myhl(divineColor)[God] that stands before his tabernacle!”


  
#versenum(30) When #myhl(menColor)[Phinehas] the priest and the chiefs of the congregation, the heads of the families of #myname[Israel] who were with him, heard the words that the people of #myname[Reuben] and the people of #myname[Gad] and the people of #myname[Manasseh] spoke, it was good in their eyes. 
#versenum(31) And #myhl(menColor)[Phinehas] the son of #myhl(menColor)[Eleazar] the priest said to the people of #myname[Reuben] and the people of #myname[Gad] and the people of #myname[Manasseh], “Today we know that the #myhl(divineColor)[#smallcaps[Lord]] is in our midst, because you have not committed this breach of faith against the #myhl(divineColor)[#smallcaps[Lord]]. Now you have delivered the people of #myname[Israel] from the hand of the #myhl(divineColor)[#smallcaps[Lord]].”


  
#versenum(32) Then #myhl(menColor)[Phinehas] the son of #myhl(menColor)[Eleazar] the priest, and the chiefs, returned from the people of #myname[Reuben] and the people of #myname[Gad] in the land of #myhl(placesColor)[Gilead] to the land of #myhl(placesColor)[Canaan], to the people of #myname[Israel], and brought back word to them. 
#versenum(33) And the report was good in the eyes of the people of #myname[Israel]. And the people of #myname[Israel] blessed #myhl(divineColor)[God] and spoke no more of making war against them to destroy the land where the people of #myname[Reuben] and the people of #myname[Gad] were settled. 
#versenum(34) The people of #myname[Reuben] and the people of #myname[Gad] called the altar Witness, “For,” they said, “it is a witness between us that the #myhl(divineColor)[#smallcaps[Lord]] is #myhl(divineColor)[God].”


  
#chapter-heading[Joshua 23]


#section-heading[Joshua’s Charge to Israel’s Leaders]


#versenum(1) A long time afterward, when the #myhl(divineColor)[#smallcaps[Lord]] had given rest to #myname[Israel] from all their surrounding enemies, and #myhl(menColor)[Joshua] was old and well advanced in years, 
#versenum(2) #myhl(menColor)[Joshua] summoned all #myname[Israel], its elders and heads, its judges and officers, and said to them, “I am now old and well advanced in years. 
#versenum(3) And you have seen all that the #myhl(divineColor)[#smallcaps[Lord] your God] has done to all these nations for your sake, for it is the #myhl(divineColor)[#smallcaps[Lord] your God] who has fought for you. 
#versenum(4) Behold, I have allotted to you as an inheritance for your tribes those nations that remain, along with all the nations that I have already cut off, from the #myhl(placesColor)[Jordan] to the #myhl(placesColor)[Great Sea] in the west. 
#versenum(5) The #myhl(divineColor)[#smallcaps[Lord] your God] will #underline[push] them back before you and drive them out of your sight. And you shall possess their land, just as the #myhl(divineColor)[#smallcaps[Lord] your God] promised you. 
#versenum(6) Therefore, be very strong to keep and to do all that is written in the Book of the Law of #myhl(menColor)[Moses], turning aside from it neither to the right hand nor to the left, 
#versenum(7) that you may not #underline[mix] with these nations remaining among you or make #underline[mention] of the names of their gods or swear by them or serve them or bow down to them, 
#versenum(8) but you shall cling to the #myhl(divineColor)[#smallcaps[Lord] your God] just as you have done to this day. 
#versenum(9) For the #myhl(divineColor)[#smallcaps[Lord]] has driven out before you great and strong nations. And as for you, no man has been able to stand before you to this day. 
#versenum(10) One man of you #underline[puts] to #underline[flight] a #mynumber[thousand], since it is the #myhl(divineColor)[#smallcaps[Lord] your God] who #underline[fights] for you, just as he promised you. 
#versenum(11) Be very careful, therefore, to love the #myhl(divineColor)[#smallcaps[Lord] your God]. 
#versenum(12) For if you turn back and cling to the remnant of these nations remaining among you and make #underline[marriages] with them, so that you #underline[associate] with them and they with you, 
#versenum(13) know for certain that the #myhl(divineColor)[#smallcaps[Lord] your God] will no longer drive out these nations before you, but they shall be a snare and a #underline[trap] for you, a #underline[whip] on your sides and thorns in your eyes, until you perish from off this good ground that the #myhl(divineColor)[#smallcaps[Lord] your God] has given you.


  
#versenum(14) “And now I am about to go the way of all the earth, and you know in your hearts and #underline[souls], all of you, that not one word has failed of all the good things#footnote[Joshua 23:14 Or #emph[words]; also twice in verse 15] that the #myhl(divineColor)[#smallcaps[Lord] your God] promised concerning you. All have come to pass for you; not one of them has failed. 
#versenum(15) But just as all the good things that the #myhl(divineColor)[#smallcaps[Lord] your God] promised concerning you have been #underline[fulfilled] for you, so the #myhl(divineColor)[#smallcaps[Lord]] will bring upon you all the evil things, until he has destroyed you from off this good land that the #myhl(divineColor)[#smallcaps[Lord] your God] has given you, 
#versenum(16) if you #underline[transgress] the covenant of the #myhl(divineColor)[#smallcaps[Lord] your God], which he commanded you, and go and serve other gods and bow down to them. Then the anger of the #myhl(divineColor)[#smallcaps[Lord]] will be kindled against you, and you shall perish quickly from off the good land that he has given to you.”


  
#chapter-heading[Joshua 24]


#section-heading[The Covenant Renewal at Shechem]


#versenum(1) #myhl(menColor)[Joshua] gathered all the tribes of #myname[Israel] to #myhl(placesColor)[Shechem] and summoned the elders, the heads, the judges, and the officers of #myname[Israel]. And they presented themselves before #myhl(divineColor)[God]. 
#versenum(2) And #myhl(menColor)[Joshua] said to all the people, “Thus says the #myhl(divineColor)[#smallcaps[Lord], the God of Israel], ‘Long #underline[ago], your fathers lived beyond the #myhl(placesColor)[Euphrates],#footnote[Joshua 24:2 Hebrew #emph[the River]] #underline[#myhl(menColor)[Terah]], the father of #myhl(menColor)[Abraham] and of #underline[#myhl(menColor)[Nahor]]; and they served other gods. 
#versenum(3) Then I took your father #myhl(menColor)[Abraham] from beyond the River#footnote[Joshua 24:3 That is, the Euphrates; also verses 14, 15] and led him through all the land of #myhl(placesColor)[Canaan], and made his offspring many. I gave him #myhl(menColor)[Isaac]. 
#versenum(4) And to #myhl(menColor)[Isaac] I gave #myhl(menColor)[Jacob] and #myhl(menColor)[Esau]. And I gave #myhl(menColor)[Esau] #myhl(placesColor)[the hill country of Seir] to possess, but #myhl(menColor)[Jacob] and his children went down to #myhl(placesColor)[Egypt]. 
#versenum(5) And I sent #myhl(menColor)[Moses] and #myhl(menColor)[Aaron], and I #underline[plagued] #myhl(placesColor)[Egypt] with what I did in the midst of it, and afterward I brought you out.


  
#versenum(6) “‘Then I brought your fathers out of #myhl(placesColor)[Egypt], and you came to the sea. And the #myname[Egyptians] pursued your fathers with chariots and #underline[horsemen] to the #myhl(placesColor)[Red Sea]. 
#versenum(7) And when they cried to the #myhl(divineColor)[#smallcaps[Lord]], he put #underline[darkness] between you and the #myname[Egyptians] and made the sea come upon them and #underline[cover] them; and your eyes saw what I did in #myhl(placesColor)[Egypt]. And you lived in the wilderness a long time. 
#versenum(8) Then I brought you to the land of the #myname[Amorites], who lived on the other side of the #myhl(placesColor)[Jordan]. They fought with you, and I gave them into your hand, and you took possession of their land, and I destroyed them before you. 
#versenum(9) Then #myhl(menColor)[Balak] the son of #myhl(menColor)[Zippor], king of #myhl(placesColor)[Moab], arose and fought against #myname[Israel]. And he sent and invited #myhl(menColor)[Balaam] the son of #myhl(menColor)[Beor] to curse you, 
#versenum(10) but I would not listen to #myhl(menColor)[Balaam]. Indeed, he blessed you. So I delivered you out of his hand. 
#versenum(11) And you went over the #myhl(placesColor)[Jordan] and came to #myhl(placesColor)[Jericho], and the leaders of #myhl(placesColor)[Jericho] fought against you, and also the #myname[Amorites], the #myname[Perizzites], the #myname[Canaanites], the #myname[Hittites], the #myname[Girgashites], the #myname[Hivites], and the #myname[Jebusites]. And I gave them into your hand. 
#versenum(12) And I sent the #underline[hornet] before you, which drove them out before you, the #mynumber[two] kings of the #myname[Amorites]; it was not by your sword or by your bow. 
#versenum(13) I gave you a land on which you had not #underline[labored] and cities that you had not built, and you dwell in them. You eat the fruit of vineyards and olive orchards that you did not #underline[plant].’


  
#section-heading[Choose Whom You Will Serve]


#versenum(14) “Now therefore fear the #myhl(divineColor)[#smallcaps[Lord]] and serve him in #underline[sincerity] and in #underline[faithfulness]. Put away the gods that your fathers served beyond the River and in #myhl(placesColor)[Egypt], and serve the #myhl(divineColor)[#smallcaps[Lord]]. 
#versenum(15) And if it is evil in your eyes to serve the #myhl(divineColor)[#smallcaps[Lord]], choose this day whom you will serve, whether the gods your fathers served in the region beyond the River, or the gods of the #myname[Amorites] in whose land you dwell. But as for me and my house, we will serve the #myhl(divineColor)[#smallcaps[Lord]].”


  
#versenum(16) Then the people answered, “Far be it from us that we should forsake the #myhl(divineColor)[#smallcaps[Lord]] to serve other gods, 
#versenum(17) for it is the #myhl(divineColor)[#smallcaps[Lord]] our #myhl(divineColor)[God] who brought us and our fathers up from the land of #myhl(placesColor)[Egypt], out of the house of slavery, and who did those great #underline[signs] in our sight and #underline[preserved] us in all the way that we went, and among all the peoples through whom we passed. 
#versenum(18) And the #myhl(divineColor)[#smallcaps[Lord]] drove out before us all the peoples, the #myname[Amorites] who lived in the land. Therefore we also will serve the #myhl(divineColor)[#smallcaps[Lord]], for he is our #myhl(divineColor)[God].”


  
#versenum(19) But #myhl(menColor)[Joshua] said to the people, “You are not able to serve the #myhl(divineColor)[#smallcaps[Lord]], for he is a holy #myhl(divineColor)[God]. He is a #underline[jealous] #myhl(divineColor)[God]; he will not #underline[forgive] your #underline[transgressions] or your #underline[sins]. 
#versenum(20) If you forsake the #myhl(divineColor)[#smallcaps[Lord]] and serve foreign gods, then he will turn and do you harm and #underline[consume] you, after having done you good.” 
#versenum(21) And the people said to #myhl(menColor)[Joshua], “No, but we will serve the #myhl(divineColor)[#smallcaps[Lord]].” 
#versenum(22) Then #myhl(menColor)[Joshua] said to the people, “You are witnesses against yourselves that you have chosen the #myhl(divineColor)[#smallcaps[Lord]], to serve him.” And they said, “We are witnesses.” 
#versenum(23) He said, “Then put away the foreign gods that are among you, and #underline[incline] your heart to the #myhl(divineColor)[#smallcaps[Lord], the God of Israel].” 
#versenum(24) And the people said to #myhl(menColor)[Joshua], “The #myhl(divineColor)[#smallcaps[Lord]] our #myhl(divineColor)[God] we will serve, and his voice we will obey.” 
#versenum(25) So #myhl(menColor)[Joshua] made a covenant with the people that day, and put in place #underline[statutes] and #underline[rules] for them at #myhl(placesColor)[Shechem]. 
#versenum(26) And #myhl(menColor)[Joshua] wrote these words in the Book of the Law of #myhl(divineColor)[God]. And he took a large stone and set it up there under the terebinth that was by the #underline[sanctuary] of the #myhl(divineColor)[#smallcaps[Lord]]. 
#versenum(27) And #myhl(menColor)[Joshua] said to all the people, “Behold, this stone shall be a witness against us, for it has heard all the words of the #myhl(divineColor)[#smallcaps[Lord]] that he spoke to us. Therefore it shall be a witness against you, lest you deal #underline[falsely] with your #myhl(divineColor)[God].” 
#versenum(28) So #myhl(menColor)[Joshua] sent the people away, every man to his inheritance.


  
#section-heading[Joshua’s Death and Burial]


#versenum(29) After these things #myhl(menColor)[Joshua] the son of #myhl(menColor)[Nun], the servant of the #myhl(divineColor)[#smallcaps[Lord]], died, being #mynumber[110] years old. 
#versenum(30) And they buried him in his own inheritance at #myhl(placesColor)[Timnath-serah], which is in #myhl(placesColor)[the hill country of Ephraim], north of the #myhl(placesColor)[mountain of Gaash].


  
#versenum(31) #myname[Israel] served the #myhl(divineColor)[#smallcaps[Lord]] all the days of #myhl(menColor)[Joshua], and all the days of the elders who outlived #myhl(menColor)[Joshua] and had known all the work that the #myhl(divineColor)[#smallcaps[Lord]] did for #myname[Israel].


  
#versenum(32) As for the #underline[bones] of #myname[Joseph], which the people of #myname[Israel] brought up from #myhl(placesColor)[Egypt], they buried them at #myhl(placesColor)[Shechem], in the #underline[piece] of land that #myhl(menColor)[Jacob] bought from the sons of #myhl(menColor)[Hamor] the father of #myhl(placesColor)[Shechem] for a #mynumber[hundred] pieces of money.#footnote[Joshua 24:32 Hebrew #emph[for a hundred qesitah]; a unit of money of uncertain value] It became an inheritance of the descendants of #myname[Joseph].


  
#versenum(33) And #myhl(menColor)[Eleazar] the son of #myhl(menColor)[Aaron] died, and they buried him at #myhl(placesColor)[Gibeah], the town of #myhl(menColor)[Phinehas] his son, which had been given him in #myhl(placesColor)[the hill country of Ephraim].


  
#chapter-heading[Judges 1]


#section-heading[The Continuing Conquest of Canaan]


#versenum(1) After the death of #myhl(menColor)[Joshua], the people of #myname[Israel] inquired of the #myhl(divineColor)[#smallcaps[Lord]], “Who shall go up #mynumber[first] for us against the #myname[Canaanites], to fight against them?” 
#versenum(2) The #myhl(divineColor)[#smallcaps[Lord]] said, “#myname[Judah] shall go up; behold, I have given the land into his hand.” 
#versenum(3) And #myname[Judah] said to #myname[Simeon] his brother, “Come up with me into the territory allotted to me, that we may fight against the #myname[Canaanites]. And I likewise will go with you into the territory allotted to you.” So #myname[Simeon] went with him. 
#versenum(4) Then #myname[Judah] went up and the #myhl(divineColor)[#smallcaps[Lord]] gave the #myname[Canaanites] and the #myname[Perizzites] into their hand, and they defeated #mynumber[10,000] of them at #myhl(placesColor)[Bezek]. 
#versenum(5) They found #myhl(menColor)[Adoni-bezek] at #myhl(placesColor)[Bezek] and fought against him and defeated the #myname[Canaanites] and the #myname[Perizzites]. 
#versenum(6) #myhl(menColor)[Adoni-bezek] fled, but they pursued him and caught him and cut off his thumbs and his big toes. 
#versenum(7) And #myhl(menColor)[Adoni-bezek] said, “#mynumber[Seventy] kings with their thumbs and their big toes cut off used to #underline[pick] up #underline[scraps] under my #underline[table]. As I have done, so #myhl(divineColor)[God] has #underline[repaid] me.” And they brought him to #myhl(placesColor)[Jerusalem], and he died there.


  
#versenum(8) And the men of #myname[Judah] fought against #myhl(placesColor)[Jerusalem] and captured it and struck it with the edge of the sword and set the city on fire. 
#versenum(9) And afterward the men of #myname[Judah] went down to fight against the #myname[Canaanites] who lived in the hill country, in the #myhl(placesColor)[Negeb], and in the lowland. 
#versenum(10) And #myname[Judah] went against the #myname[Canaanites] who lived in #myhl(placesColor)[Hebron] (now the name of #myhl(placesColor)[Hebron] was formerly #myhl(placesColor)[Kiriath-arba]), and they defeated #myhl(menColor)[Sheshai] and #myhl(menColor)[Ahiman] and #myhl(menColor)[Talmai].


  
#versenum(11) From there they went against the inhabitants of #myhl(placesColor)[Debir]. The name of #myhl(placesColor)[Debir] was formerly #myhl(placesColor)[Kiriath-sepher]. 
#versenum(12) And #myhl(menColor)[Caleb] said, “He who attacks #myhl(placesColor)[Kiriath-sepher] and captures it, I will give him #myhl(womenColor)[Achsah] my daughter as wife.” 
#versenum(13) And #myhl(menColor)[Othniel] the son of #myhl(menColor)[Kenaz], #myhl(menColor)[Caleb]’s younger brother, captured it. And he gave him #myhl(womenColor)[Achsah] his daughter as wife. 
#versenum(14) When she came to him, she urged him to ask her father for a field. And she dismounted from her donkey, and #myhl(menColor)[Caleb] said to her, “What do you want?” 
#versenum(15) She said to him, “Give me a blessing. Since you have given me the land of the #myhl(placesColor)[Negeb], give me also springs of water.” And #myhl(menColor)[Caleb] gave her the upper springs and the lower springs.


  
#versenum(16) And the descendants of the #myname[Kenite], #myhl(menColor)[Moses]’ father-in-law, went up with the people of #myname[Judah] from the city of palms into the wilderness of #myname[Judah], which lies in the #myhl(placesColor)[Negeb] near #myhl(placesColor)[Arad], and they went and settled with the people. 
#versenum(17) And #myname[Judah] went with #myname[Simeon] his brother, and they defeated the #myname[Canaanites] who inhabited #underline[#myhl(placesColor)[Zephath]] and devoted it to destruction. So the name of the city was called #myhl(placesColor)[Hormah].#footnote[Judges 1:17 #emph[Hormah] means #emph[utter destruction]] 
#versenum(18) #myname[Judah] also captured #myhl(placesColor)[Gaza] with its territory, and #myhl(placesColor)[Ashkelon] with its territory, and #myhl(placesColor)[Ekron] with its territory. 
#versenum(19) And the #myhl(divineColor)[#smallcaps[Lord]] was with #myname[Judah], and he took possession of the hill country, but he could not drive out the inhabitants of the plain because they had chariots of iron. 
#versenum(20) And #myhl(placesColor)[Hebron] was given to #myhl(menColor)[Caleb], as #myhl(menColor)[Moses] had said. And he drove out from it the #mynumber[three] sons of #myhl(menColor)[Anak]. 
#versenum(21) But the people of #myname[Benjamin] did not drive out the #myname[Jebusites] who lived in #myhl(placesColor)[Jerusalem], so the #myname[Jebusites] have lived with the people of #myname[Benjamin] in #myhl(placesColor)[Jerusalem] to this day.


  
#versenum(22) The house of #myname[Joseph] also went up against #myhl(placesColor)[Bethel], and the #myhl(divineColor)[#smallcaps[Lord]] was with them. 
#versenum(23) And the house of #myname[Joseph] #underline[scouted] out #myhl(placesColor)[Bethel]. (Now the name of the city was formerly #myhl(placesColor)[Luz].) 
#versenum(24) And the spies saw a man coming out of the city, and they said to him, “Please show us the way into the city, and we will deal kindly with you.” 
#versenum(25) And he #underline[showed] them the way into the city. And they struck the city with the edge of the sword, but they let the man and all his family go. 
#versenum(26) And the man went to the land of the #myname[Hittites] and built a city and called its name #myhl(placesColor)[Luz]. That is its name to this day.


  
#section-heading[Failure to Complete the Conquest]


#versenum(27) #myname[Manasseh] did not drive out the inhabitants of #myhl(placesColor)[Beth-shean] and its villages, or #myhl(placesColor)[Taanach] and its villages, or the inhabitants of #myhl(placesColor)[Dor] and its villages, or the inhabitants of #myhl(placesColor)[Ibleam] and its villages, or the inhabitants of #myhl(placesColor)[Megiddo] and its villages, for the #myname[Canaanites] persisted in dwelling in that land. 
#versenum(28) When #myname[Israel] grew strong, they put the #myname[Canaanites] to forced labor, but did not drive them out completely.


  
#versenum(29) And #myname[Ephraim] did not drive out the #myname[Canaanites] who lived in #myhl(placesColor)[Gezer], so the #myname[Canaanites] lived in #myhl(placesColor)[Gezer] among them.


  
#versenum(30) #myname[Zebulun] did not drive out the inhabitants of #underline[#myhl(placesColor)[Kitron]], or the inhabitants of #underline[#myhl(placesColor)[Nahalol]], so the #myname[Canaanites] lived among them, but became subject to forced labor.


  
#versenum(31) #myname[Asher] did not drive out the inhabitants of #underline[#myhl(placesColor)[Acco]], or the inhabitants of #myhl(placesColor)[Sidon] or of #underline[#myhl(placesColor)[Ahlab]] or of #myhl(placesColor)[Achzib] or of #underline[#myhl(placesColor)[Helbah]] or of #underline[#myhl(placesColor)[Aphik]] or of #myhl(placesColor)[Rehob], 
#versenum(32) so the #underline[#myname[Asherites]] lived among the #myname[Canaanites], the inhabitants of the land, for they did not drive them out.


  
#versenum(33) #myname[Naphtali] did not drive out the inhabitants of #myhl(placesColor)[Beth-shemesh], or the inhabitants of #myhl(placesColor)[Beth-anath], so they lived among the #myname[Canaanites], the inhabitants of the land. Nevertheless, the inhabitants of #myhl(placesColor)[Beth-shemesh] and of #myhl(placesColor)[Beth-anath] became subject to forced labor for them.


  
#versenum(34) The #myname[Amorites] pressed the people of #myname[Dan] back into the hill country, for they did not allow them to come down to the plain. 
#versenum(35) The #myname[Amorites] persisted in dwelling in #myhl(placesColor)[Mount Heres], in #myhl(placesColor)[Aijalon], and in #underline[#myhl(placesColor)[Shaalbim]], but the hand of the house of #myname[Joseph] rested #underline[heavily] on them, and they became subject to forced labor. 
#versenum(36) And the border of the #myname[Amorites] ran from the ascent of #myhl(placesColor)[Akrabbim], from #underline[#myhl(placesColor)[Sela]] and #underline[upward].


  
#chapter-heading[Judges 2]


#section-heading[Israel’s Disobedience]


#versenum(1) Now the #myhl(divineColor)[angel of the #smallcaps[Lord]] went up from #myhl(placesColor)[Gilgal] to #myhl(placesColor)[Bochim]. And he said, “I brought you up from #myhl(placesColor)[Egypt] and brought you into the land that I swore to give to your fathers. I said, ‘I will never break my covenant with you, 
#versenum(2) and you shall make no covenant with the inhabitants of this land; you shall break down their #underline[altars].’ But you have not obeyed my voice. What is this you have done? 
#versenum(3) So now I say, I will not drive them out before you, but they shall become thorns in your sides, and their gods shall be a snare to you.” 
#versenum(4) As soon as the #myhl(divineColor)[angel of the #smallcaps[Lord]] spoke these words to all the people of #myname[Israel], the people lifted up their voices and wept. 
#versenum(5) And they called the name of that place #myhl(placesColor)[Bochim].#footnote[Judges 2:5 #emph[Bochim] means #emph[weepers]] And they sacrificed there to the #myhl(divineColor)[#smallcaps[Lord]].


  
#section-heading[The Death of Joshua]


#versenum(6) When #myhl(menColor)[Joshua] #underline[dismissed] the people, the people of #myname[Israel] went each to his inheritance to take possession of the land. 
#versenum(7) And the people served the #myhl(divineColor)[#smallcaps[Lord]] all the days of #myhl(menColor)[Joshua], and all the days of the elders who outlived #myhl(menColor)[Joshua], who had seen all the great work that the #myhl(divineColor)[#smallcaps[Lord]] had done for #myname[Israel]. 
#versenum(8) And #myhl(menColor)[Joshua] the son of #myhl(menColor)[Nun], the servant of the #myhl(divineColor)[#smallcaps[Lord]], died at the age of #mynumber[110] years. 
#versenum(9) And they buried him within the #underline[boundaries] of his inheritance in #underline[#myhl(placesColor)[Timnath-heres]], in #myhl(placesColor)[the hill country of Ephraim], north of the #myhl(placesColor)[mountain of Gaash]. 
#versenum(10) And all that generation also were gathered to their fathers. And there arose another generation after them who did not know the #myhl(divineColor)[#smallcaps[Lord]] or the work that he had done for #myname[Israel].


  
#section-heading[Israel’s Unfaithfulness]


#versenum(11) And the people of #myname[Israel] did what was evil in the sight of the #myhl(divineColor)[#smallcaps[Lord]] and served the #myname[Baals]. 
#versenum(12) And they abandoned the #myhl(divineColor)[#smallcaps[Lord], the God] of their fathers, who had brought them out of the land of #myhl(placesColor)[Egypt]. They went after other gods, from among the gods of the peoples who were around them, and bowed down to them. And they #underline[provoked] the #myhl(divineColor)[#smallcaps[Lord]] to anger. 
#versenum(13) They abandoned the #myhl(divineColor)[#smallcaps[Lord]] and served the #myname[Baals] and the #myname[Ashtaroth]. 
#versenum(14) So the anger of the #myhl(divineColor)[#smallcaps[Lord]] was kindled against #myname[Israel], and he gave them over to #underline[plunderers], who plundered them. And he sold them into the hand of their surrounding enemies, so that they could no longer #underline[withstand] their enemies. 
#versenum(15) Whenever they marched out, the hand of the #myhl(divineColor)[#smallcaps[Lord]] was against them for harm, as the #myhl(divineColor)[#smallcaps[Lord]] had #underline[warned], and as the #myhl(divineColor)[#smallcaps[Lord]] had sworn to them. And they were in #underline[terrible] distress.


  
#section-heading[The LORD Raises Up Judges]


#versenum(16) Then the #myhl(divineColor)[#smallcaps[Lord]] raised up judges, who saved them out of the hand of those who plundered them. 
#versenum(17) Yet they did not listen to their judges, for they whored after other gods and bowed down to them. They soon turned aside from the way in which their fathers had walked, who had obeyed the commandments of the #myhl(divineColor)[#smallcaps[Lord]], and they did not do so. 
#versenum(18) Whenever the #myhl(divineColor)[#smallcaps[Lord]] raised up judges for them, the #myhl(divineColor)[#smallcaps[Lord]] was with the judge, and he saved them from the hand of their enemies all the days of the judge. For the #myhl(divineColor)[#smallcaps[Lord]] was moved to #underline[pity] by their #underline[groaning] because of those who #underline[afflicted] and oppressed them. 
#versenum(19) But whenever the judge died, they turned back and were more #underline[corrupt] than their fathers, going after other gods, #underline[serving] them and bowing down to them. They did not #underline[drop] any of their #underline[practices] or their #underline[stubborn] ways. 
#versenum(20) So the anger of the #myhl(divineColor)[#smallcaps[Lord]] was kindled against #myname[Israel], and he said, “Because this people have transgressed my covenant that I commanded their fathers and have not obeyed my voice, 
#versenum(21) I will no longer drive out before them any of the nations that #myhl(menColor)[Joshua] left when he died, 
#versenum(22) in order to test #myname[Israel] by them, whether they will take care to walk in the way of the #myhl(divineColor)[#smallcaps[Lord]] as their fathers did, or not.” 
#versenum(23) So the #myhl(divineColor)[#smallcaps[Lord]] left those nations, not #underline[driving] them out quickly, and he did not give them into the hand of #myhl(menColor)[Joshua].


  
#chapter-heading[Judges 3]


#versenum(1) Now these are the nations that the #myhl(divineColor)[#smallcaps[Lord]] left, to test #myname[Israel] by them, that is, all in #myhl(placesColor)[Israel] who had not #underline[experienced] all the #underline[wars] in #myhl(placesColor)[Canaan]. 
#versenum(2) It was only in order that the generations of the people of #myname[Israel] might know war, to teach war to those who had not known it before. 
#versenum(3) These are the nations: the #mynumber[five] lords of the #myname[Philistines] and all the #myname[Canaanites] and the #myname[Sidonians] and the #myname[Hivites] who lived on #myhl(placesColor)[Mount Lebanon], from Mount #underline[#myhl(placesColor)[Baal-hermon]] as far as #myhl(placesColor)[Lebo-hamath]. 
#versenum(4) They were for the #underline[testing] of #myname[Israel], to know whether #myname[Israel] would obey the commandments of the #myhl(divineColor)[#smallcaps[Lord]], which he commanded their fathers by the hand of #myhl(menColor)[Moses]. 
#versenum(5) So the people of #myname[Israel] lived among the #myname[Canaanites], the #myname[Hittites], the #myname[Amorites], the #myname[Perizzites], the #myname[Hivites], and the #myname[Jebusites]. 
#versenum(6) And their daughters they took to themselves for wives, and their own daughters they gave to their sons, and they served their gods.


  
#section-heading[Othniel]


#versenum(7) And the people of #myname[Israel] did what was evil in the sight of the #myhl(divineColor)[#smallcaps[Lord]]. They #underline[forgot] the #myhl(divineColor)[#smallcaps[Lord]] their #myhl(divineColor)[God] and served the #myname[Baals] and the #underline[#myname[Asheroth]]. 
#versenum(8) Therefore the anger of the #myhl(divineColor)[#smallcaps[Lord]] was kindled against #myname[Israel], and he sold them into the hand of #myhl(menColor)[Cushan-rishathaim] king of Mesopotamia. And the people of #myname[Israel] served #myhl(menColor)[Cushan-rishathaim] #mynumber[eight] years. 
#versenum(9) But when the people of #myname[Israel] cried out to the #myhl(divineColor)[#smallcaps[Lord]], the #myhl(divineColor)[#smallcaps[Lord]] raised up a deliverer for the people of #myname[Israel], who saved them, #myhl(menColor)[Othniel] the son of #myhl(menColor)[Kenaz], #myhl(menColor)[Caleb]’s younger brother. 
#versenum(10) The #myhl(divineColor)[Spirit] of the #myhl(divineColor)[#smallcaps[Lord]] was upon him, and he judged #myname[Israel]. He went out to war, and the #myhl(divineColor)[#smallcaps[Lord]] gave #myhl(menColor)[Cushan-rishathaim] king of Mesopotamia into his hand. And his hand #underline[prevailed] over #myhl(menColor)[Cushan-rishathaim]. 
#versenum(11) So the land had rest for #mynumber[forty] years. Then #myhl(menColor)[Othniel] the son of #myhl(menColor)[Kenaz] died.


  
#section-heading[Ehud]


#versenum(12) And the people of #myname[Israel] again did what was evil in the sight of the #myhl(divineColor)[#smallcaps[Lord]], and the #myhl(divineColor)[#smallcaps[Lord]] strengthened #myhl(menColor)[Eglon] the king of #myhl(placesColor)[Moab] against #myname[Israel], because they had done what was evil in the sight of the #myhl(divineColor)[#smallcaps[Lord]]. 
#versenum(13) He gathered to himself the #myname[Ammonites] and the #myname[Amalekites], and went and defeated #myname[Israel]. And they took possession of the city of palms. 
#versenum(14) And the people of #myname[Israel] served #myhl(menColor)[Eglon] the king of #myhl(placesColor)[Moab] #mynumber[eighteen] years.


  
#versenum(15) Then the people of #myname[Israel] cried out to the #myhl(divineColor)[#smallcaps[Lord]], and the #myhl(divineColor)[#smallcaps[Lord]] raised up for them a deliverer, #myhl(menColor)[Ehud], the son of #underline[#myhl(menColor)[Gera]], the #underline[#myname[Benjaminite]], a left-handed man. The people of #myname[Israel] sent tribute by him to #myhl(menColor)[Eglon] the king of #myhl(placesColor)[Moab]. 
#versenum(16) And #myhl(menColor)[Ehud] made for himself a sword with #mynumber[two] #underline[edges], a #underline[cubit]#footnote[Judges 3:16 A #emph[cubit] was about 18 inches or 45 centimeters] in length, and he bound it on his right thigh under his clothes. 
#versenum(17) And he presented the tribute to #myhl(menColor)[Eglon] king of #myhl(placesColor)[Moab]. Now #myhl(menColor)[Eglon] was a very fat man. 
#versenum(18) And when #myhl(menColor)[Ehud] had finished #underline[presenting] the tribute, he sent away the people who carried the tribute. 
#versenum(19) But he himself turned back at the idols near #myhl(placesColor)[Gilgal] and said, “I have a secret message for you, O king.” And he commanded, “#underline[Silence].” And all his #underline[attendants] went out from his presence. 
#versenum(20) And #myhl(menColor)[Ehud] came to him as he was sitting alone in his cool roof chamber. And #myhl(menColor)[Ehud] said, “I have a message from #myhl(divineColor)[God] for you.” And he arose from his #underline[seat]. 
#versenum(21) And #myhl(menColor)[Ehud] reached with his left hand, took the sword from his right thigh, and thrust it into his belly. 
#versenum(22) And the #underline[hilt] also went in after the blade, and the fat closed over the blade, for he did not pull the sword out of his belly; and the #underline[dung] came out. 
#versenum(23) Then #myhl(menColor)[Ehud] went out into the #underline[porch]#footnote[Judges 3:23 The meaning of the Hebrew word is uncertain] and closed the doors of the roof chamber behind him and locked them.


  
#versenum(24) When he had gone, the servants came, and when they saw that the doors of the roof chamber were locked, they thought, “Surely he is #underline[relieving] himself in the #underline[closet] of the cool chamber.” 
#versenum(25) And they #underline[waited] till they were #underline[embarrassed]. But when he still did not open the doors of the roof chamber, they took the #underline[key] and opened them, and there lay their lord dead on the floor.


  
#versenum(26) #myhl(menColor)[Ehud] escaped while they #underline[delayed], and he passed beyond the idols and escaped to #underline[#myhl(placesColor)[Seirah]]. 
#versenum(27) When he arrived, he sounded the trumpet in #myhl(placesColor)[the hill country of Ephraim]. Then the people of #myname[Israel] went down with him from the hill country, and he was their leader. 
#versenum(28) And he said to them, “Follow after me, for the #myhl(divineColor)[#smallcaps[Lord]] has given your enemies the #myname[Moabites] into your hand.” So they went down after him and seized the fords of the #myhl(placesColor)[Jordan] against the #myname[Moabites] and did not allow anyone to pass over. 
#versenum(29) And they killed at that time about #mynumber[10,000] of the #myname[Moabites], all strong, #underline[able-bodied] men; not a man escaped. 
#versenum(30) So #myhl(placesColor)[Moab] was subdued that day under the hand of #myname[Israel]. And the land had rest for #underline[#mynumber[eighty]] years.


  
#section-heading[Shamgar]


#versenum(31) After him was #myhl(menColor)[Shamgar] the son of #myhl(menColor)[Anath], who killed #mynumber[600] of the #myname[Philistines] with an #underline[oxgoad], and he also saved #myname[Israel].


  
#chapter-heading[Judges 4]


#section-heading[Deborah and Barak]


#versenum(1) And the people of #myname[Israel] again did what was evil in the sight of the #myhl(divineColor)[#smallcaps[Lord]] after #myhl(menColor)[Ehud] died. 
#versenum(2) And the #myhl(divineColor)[#smallcaps[Lord]] sold them into the hand of #myhl(menColor)[Jabin] king of #myhl(placesColor)[Canaan], who reigned in #myhl(placesColor)[Hazor]. The commander of his army was #myhl(menColor)[Sisera], who lived in #myhl(placesColor)[Harosheth-hagoyim]. 
#versenum(3) Then the people of #myname[Israel] cried out to the #myhl(divineColor)[#smallcaps[Lord]] for help, for he had #mynumber[900] chariots of iron and he oppressed the people of #myname[Israel] #underline[cruelly] for #mynumber[twenty] years.


  
#versenum(4) Now #myhl(womenColor)[Deborah], a #underline[prophetess], the wife of #underline[#myhl(menColor)[Lappidoth]], was #underline[judging] #myname[Israel] at that time. 
#versenum(5) She used to sit under the #underline[palm] of #myhl(womenColor)[Deborah] between #myhl(placesColor)[Ramah] and #myhl(placesColor)[Bethel] in #myhl(placesColor)[the hill country of Ephraim], and the people of #myname[Israel] came up to her for judgment. 
#versenum(6) She sent and summoned #myhl(menColor)[Barak] the son of #myhl(menColor)[Abinoam] from #underline[#myhl(placesColor)[Kedesh-naphtali]] and said to him, “Has not the #myhl(divineColor)[#smallcaps[Lord], the God of Israel], commanded you, ‘Go, gather your men at #myhl(placesColor)[Mount Tabor], taking #mynumber[10,000] from the people of #myname[Naphtali] and the people of #myname[Zebulun]. 
#versenum(7) And I will draw out #myhl(menColor)[Sisera], the #underline[general] of #underline[#myhl(menColor)[Jabin]’s] army, to meet you by the river #myhl(placesColor)[Kishon] with his chariots and his troops, and I will give him into your hand’?” 
#versenum(8) #myhl(menColor)[Barak] said to her, “If you will go with me, I will go, but if you will not go with me, I will not go.” 
#versenum(9) And she said, “I will surely go with you. Nevertheless, the #underline[road] on which you are going will not lead to your glory, for the #myhl(divineColor)[#smallcaps[Lord]] will #underline[sell] #myhl(menColor)[Sisera] into the hand of a woman.” Then #myhl(womenColor)[Deborah] arose and went with #myhl(menColor)[Barak] to #myhl(placesColor)[Kedesh]. 
#versenum(10) And #myhl(menColor)[Barak] called out #myname[Zebulun] and #myname[Naphtali] to #myhl(placesColor)[Kedesh]. And #mynumber[10,000] men went up at his heels, and #myhl(womenColor)[Deborah] went up with him.


  
#versenum(11) Now #myhl(menColor)[Heber] the #myname[Kenite] had #underline[separated] from the #underline[#myname[Kenites]], the descendants of #underline[#myhl(menColor)[Hobab]] the father-in-law of #myhl(menColor)[Moses], and had #underline[pitched] his tent as far away as the oak in #myhl(placesColor)[Zaanannim], which is near #myhl(placesColor)[Kedesh].


  
#versenum(12) When #myhl(menColor)[Sisera] was told that #myhl(menColor)[Barak] the son of #myhl(menColor)[Abinoam] had gone up to #myhl(placesColor)[Mount Tabor], 
#versenum(13) #myhl(menColor)[Sisera] called out all his chariots, #mynumber[900] chariots of iron, and all the men who were with him, from #myhl(placesColor)[Harosheth-hagoyim] to the river #myhl(placesColor)[Kishon]. 
#versenum(14) And #myhl(womenColor)[Deborah] said to #myhl(menColor)[Barak], “Up! For this is the day in which the #myhl(divineColor)[#smallcaps[Lord]] has given #myhl(menColor)[Sisera] into your hand. Does not the #myhl(divineColor)[#smallcaps[Lord]] go out before you?” So #myhl(menColor)[Barak] went down from #myhl(placesColor)[Mount Tabor] with #mynumber[10,000] men following him. 
#versenum(15) And the #myhl(divineColor)[#smallcaps[Lord]] routed #myhl(menColor)[Sisera] and all his chariots and all his army before #myhl(menColor)[Barak] by the edge of the sword. And #myhl(menColor)[Sisera] got down from his chariot and fled away on foot. 
#versenum(16) And #myhl(menColor)[Barak] pursued the chariots and the army to #myhl(placesColor)[Harosheth-hagoyim], and all the army of #myhl(menColor)[Sisera] fell by the edge of the sword; not a man was left.


  
#versenum(17) But #myhl(menColor)[Sisera] fled away on foot to the tent of #myhl(womenColor)[Jael], the wife of #myhl(menColor)[Heber] the #myname[Kenite], for there was peace between #myhl(menColor)[Jabin] the king of #myhl(placesColor)[Hazor] and the house of #myhl(menColor)[Heber] the #myname[Kenite]. 
#versenum(18) And #myhl(womenColor)[Jael] came out to meet #myhl(menColor)[Sisera] and said to him, “Turn aside, my lord; turn aside to me; do not be afraid.” So he turned aside to her into the tent, and she covered him with a #underline[rug]. 
#versenum(19) And he said to her, “Please give me a little water to drink, for I am thirsty.” So she opened a #underline[skin] of milk and gave him a drink and covered him. 
#versenum(20) And he said to her, “Stand at the #underline[opening] of the tent, and if any man comes and #underline[asks] you, ‘Is anyone here?’ say, ‘No.’” 
#versenum(21) But #myhl(womenColor)[Jael] the wife of #myhl(menColor)[Heber] took a tent peg, and took a #underline[hammer] in her hand. Then she went softly to him and drove the peg into his temple until it went down into the ground while he was lying #underline[fast] #underline[asleep] from #underline[weariness]. So he died. 
#versenum(22) And behold, as #myhl(menColor)[Barak] was pursuing #myhl(menColor)[Sisera], #myhl(womenColor)[Jael] went out to meet him and said to him, “Come, and I will show you the man whom you are seeking.” So he went in to her tent, and there lay #myhl(menColor)[Sisera] dead, with the tent peg in his temple.


  
#versenum(23) So on that day #myhl(divineColor)[God] subdued #myhl(menColor)[Jabin] the king of #myhl(placesColor)[Canaan] before the people of #myname[Israel]. 
#versenum(24) And the hand of the people of #myname[Israel] pressed harder and harder against #myhl(menColor)[Jabin] the king of #myhl(placesColor)[Canaan], until they destroyed #myhl(menColor)[Jabin] king of #myhl(placesColor)[Canaan].


  
#chapter-heading[Judges 5]


#section-heading[The Song of Deborah and Barak]


#versenum(1) Then #underline[sang] #myhl(womenColor)[Deborah] and #myhl(menColor)[Barak] the son of #myhl(menColor)[Abinoam] on that day:


    
#versenum(2) “That the leaders took the lead in #myhl(placesColor)[Israel],\
    #vin that the people offered themselves willingly,\
    #vin bless the #myhl(divineColor)[#smallcaps[Lord]]!\


    
#versenum(3) “Hear, O kings; give #underline[ear], O princes;\
    #vin to the #myhl(divineColor)[#smallcaps[Lord]] I will #underline[sing];\
    #vin I will make #underline[melody] to the #myhl(divineColor)[#smallcaps[Lord], the God of Israel].\


    
#versenum(4) “#myhl(divineColor)[#smallcaps[Lord]], when you went out from #myhl(placesColor)[Seir],\
    #vin when you marched from the region of #myhl(placesColor)[Edom],\
    the earth #underline[trembled]\
    #vin and the heavens dropped,\
    #vin #underline[yes], the #underline[clouds] dropped water.\
    
#versenum(5) The mountains #underline[quaked] before the #myhl(divineColor)[#smallcaps[Lord]],\
    #vin even #underline[#myhl(placesColor)[Sinai]] before the #myhl(divineColor)[#smallcaps[Lord],#footnote[Judges 5:5 Or #emph[before the Lord, the One of Sinai, before the Lord]] the God of Israel].\


    
#versenum(6) “In the days of #myhl(menColor)[Shamgar], son of #myhl(menColor)[Anath],\
    #vin in the days of #myhl(womenColor)[Jael], the highways were abandoned,\
    #vin and #underline[travelers] kept to the #underline[byways].\
    
#versenum(7) The villagers ceased in #myhl(placesColor)[Israel];\
    #vin they ceased to be until I arose;\
    #vin I, #myhl(womenColor)[Deborah], arose as a mother in #myhl(placesColor)[Israel].\
    
#versenum(8) When new gods were chosen,\
    #vin then war was in the gates.\
    Was #underline[shield] or #underline[spear] to be seen\
    #vin among #mynumber[forty thousand] in #myhl(placesColor)[Israel]?\
    
#versenum(9) My heart goes out to the commanders of #myname[Israel]\
    #vin who offered themselves willingly among the people.\
    #vin Bless the #myhl(divineColor)[#smallcaps[Lord]].\


    
#versenum(10) “Tell of it, you who #underline[ride] on #underline[white] donkeys,\
    #vin you who sit on rich #underline[carpets]#footnote[Judges 5:10 The meaning of the Hebrew word is uncertain; it may connote #emph[saddle blankets]]\
    #vin and you who walk by the way.\
    
#versenum(11) To the sound of #underline[musicians]#footnote[Judges 5:11 Or #emph[archers]; the meaning of the Hebrew word is uncertain] at the #underline[watering] places,\
    #vin there they #underline[repeat] the righteous triumphs of the #myhl(divineColor)[#smallcaps[Lord]],\
    #vin the righteous triumphs of his villagers in #myhl(placesColor)[Israel].\


    “Then down to the gates marched the people of the #myhl(divineColor)[#smallcaps[Lord]].\


    
#versenum(12) “Awake, awake, #myhl(womenColor)[Deborah]!\
    #vin Awake, awake, break out in a #underline[song]!\
    Arise, #myhl(menColor)[Barak], lead away your #underline[captives],\
    #vin O son of #myhl(menColor)[Abinoam].\
    
#versenum(13) Then down marched the remnant of the #underline[noble];\
    #vin the people of the #myhl(divineColor)[#smallcaps[Lord]] marched down for me against the mighty.\
    
#versenum(14) From #myname[Ephraim] their #underline[root] they marched down into the valley,#footnote[Judges 5:14 Septuagint; Hebrew #emph[in Amalek]]\
    #vin following you, #myname[Benjamin], with your #underline[kinsmen];\
    from #myhl(menColor)[Machir] marched down the commanders,\
    #vin and from #myname[Zebulun] those who bear the #underline[lieutenant’s]#footnote[Judges 5:14 Hebrew #emph[commander’s]] staff;\
    
#versenum(15) the princes of #myname[Issachar] came with #myhl(womenColor)[Deborah],\
    #vin and #myname[Issachar] #underline[faithful] to #myhl(menColor)[Barak];\
    #vin into the valley they rushed at his heels.\
    Among the clans of #myname[Reuben]\
    #vin there were great searchings of heart.\
    
#versenum(16) Why did you sit still among the #underline[sheepfolds],\
    #vin to hear the #underline[whistling] for the #underline[flocks]?\
    Among the clans of #myname[Reuben]\
    #vin there were great searchings of heart.\
    
#versenum(17) #myhl(placesColor)[Gilead] stayed beyond the #myhl(placesColor)[Jordan];\
    #vin and #myname[Dan], why did he stay with the #underline[ships]?\
    #myname[Asher] sat still at the coast of the sea,\
    #vin #underline[staying] by his #underline[landings].\
    
#versenum(18) #myname[Zebulun] is a people who risked their lives to the death;\
    #vin #myname[Naphtali], too, on the #underline[heights] of the field.\


    
#versenum(19) “The kings came, they fought;\
    #vin then fought the kings of #myhl(placesColor)[Canaan],\
    at #myhl(placesColor)[Taanach], by the waters of #myhl(placesColor)[Megiddo];\
    #vin they got no #underline[spoils] of silver.\
    
#versenum(20) From heaven the #underline[stars] fought,\
    #vin from their #underline[courses] they fought against #myhl(menColor)[Sisera].\
    
#versenum(21) The torrent #myhl(placesColor)[Kishon] #underline[swept] them away,\
    #vin the #underline[ancient] torrent, the torrent #myhl(placesColor)[Kishon].\
    #vin March on, my soul, with might!\


    
#versenum(22) “Then #underline[loud] beat the horses’ #underline[hoofs]\
    #vin with the galloping, galloping of his #underline[steeds].\


    
#versenum(23) “Curse #underline[#myhl(placesColor)[Meroz]], says the #myhl(divineColor)[angel of the #smallcaps[Lord]],\
    #vin curse its inhabitants #underline[thoroughly],\
    because they did not come to the help of the #myhl(divineColor)[#smallcaps[Lord]],\
    #vin to the help of the #myhl(divineColor)[#smallcaps[Lord]] against the mighty.\


    
#versenum(24) “Most blessed of women be #myhl(womenColor)[Jael],\
    #vin the wife of #myhl(menColor)[Heber] the #myname[Kenite],\
    #vin of #underline[tent-dwelling] women most blessed.\
    
#versenum(25) He asked for water and she gave him milk;\
    #vin she brought him #underline[curds] in a #underline[noble’s] bowl.\
    
#versenum(26) She sent her hand to the tent peg\
    #vin and her right hand to the #underline[workmen’s] #underline[mallet];\
    she struck #myhl(menColor)[Sisera];\
    #vin she crushed his head;\
    #vin she #underline[shattered] and #underline[pierced] his temple.\
    
#versenum(27) Between her feet\
    #vin he sank, he fell, he lay still;\
    between her feet\
    #vin he sank, he fell;\
    where he sank,\
    #vin there he fell—dead.\


    
#versenum(28) “Out of the window she #underline[peered],\
    #vin the mother of #myhl(menColor)[Sisera] #underline[wailed] through the #underline[lattice]:\
    ‘Why is his chariot so long in coming?\
    #vin Why #underline[tarry] the #underline[hoofbeats] of his chariots?’\
    
#versenum(29) Her #underline[wisest] #underline[princesses] answer,\
    #vin indeed, she #underline[answers] #underline[herself],\
    
#versenum(30) ‘Have they not found and divided the spoil?—\
    #vin A womb or #mynumber[two] for every man;\
    spoil of dyed materials for #myhl(menColor)[Sisera],\
    #vin spoil of dyed materials embroidered,\
    #vin #mynumber[two] pieces of dyed work embroidered for the #underline[neck] as spoil?’\


    
#versenum(31) “So may all your enemies perish, O #myhl(divineColor)[#smallcaps[Lord]]!\
    #vin But your #underline[friends] be like the sun as he rises in his might.”\


      And the land had rest for #mynumber[forty] years.


  
#chapter-heading[Judges 6]


#section-heading[Midian Oppresses Israel]


#versenum(1) The people of #myname[Israel] did what was evil in the sight of the #myhl(divineColor)[#smallcaps[Lord]], and the #myhl(divineColor)[#smallcaps[Lord]] gave them into the hand of #myname[Midian] #mynumber[seven] years. 
#versenum(2) And the hand of #myname[Midian] #underline[overpowered] #myname[Israel], and because of #myname[Midian] the people of #myname[Israel] made for themselves the #underline[dens] that are in the mountains and the #underline[caves] and the #underline[strongholds]. 
#versenum(3) For whenever the #myname[Israelites] #underline[planted] #underline[crops], the #myname[Midianites] and the #myname[Amalekites] and the people of the East would come up against them. 
#versenum(4) They would #underline[encamp] against them and devour the produce of the land, as far as #myhl(placesColor)[Gaza], and leave no #underline[sustenance] in #myhl(placesColor)[Israel] and no sheep or #underline[ox] or donkey. 
#versenum(5) For they would come up with their livestock and their tents; they would come like locusts in number—both they and their camels could not be counted—so that they laid #underline[waste] the land as they came in. 
#versenum(6) And #myname[Israel] was brought very low because of #myname[Midian]. And the people of #myname[Israel] cried out for help to the #myhl(divineColor)[#smallcaps[Lord]].


  
#versenum(7) When the people of #myname[Israel] cried out to the #myhl(divineColor)[#smallcaps[Lord]] on account of the #myname[Midianites], 
#versenum(8) the #myhl(divineColor)[#smallcaps[Lord]] sent a #underline[prophet] to the people of #myname[Israel]. And he said to them, “Thus says the #myhl(divineColor)[#smallcaps[Lord], the God of Israel]: I led you up from #myhl(placesColor)[Egypt] and brought you out of the house of slavery. 
#versenum(9) And I delivered you from the hand of the #myname[Egyptians] and from the hand of all who oppressed you, and drove them out before you and gave you their land. 
#versenum(10) And I said to you, ‘I am the #myhl(divineColor)[#smallcaps[Lord] your God]; you shall not fear the gods of the #myname[Amorites] in whose land you dwell.’ But you have not obeyed my voice.”


  
#section-heading[The Call of Gideon]


#versenum(11) Now the #myhl(divineColor)[angel of the #smallcaps[Lord]] came and sat under the terebinth at #myhl(placesColor)[Ophrah], which belonged to #myhl(menColor)[Joash] the #underline[#myname[Abiezrite]], while his son #myhl(menColor)[Gideon] was beating out wheat in the winepress to hide it from the #myname[Midianites]. 
#versenum(12) And the #myhl(divineColor)[angel of the #smallcaps[Lord]] appeared to him and said to him, “The #myhl(divineColor)[#smallcaps[Lord]] is with you, O mighty man of valor.” 
#versenum(13) And #myhl(menColor)[Gideon] said to him, “Please, my lord, if the #myhl(divineColor)[#smallcaps[Lord]] is with us, why then has all this happened to us? And where are all his wonderful deeds that our fathers #underline[recounted] to us, saying, ‘Did not the #myhl(divineColor)[#smallcaps[Lord]] bring us up from #myhl(placesColor)[Egypt]?’ But now the #myhl(divineColor)[#smallcaps[Lord]] has forsaken us and given us into the hand of #myname[Midian].” 
#versenum(14) And the #myhl(divineColor)[#smallcaps[Lord]]#footnote[Judges 6:14 Septuagint #emph[the angel of the Lord]; also verse 16] turned to him and said, “Go in this might of yours and save #myname[Israel] from the hand of #myname[Midian]; do not I send you?” 
#versenum(15) And he said to him, “Please, Lord, how can I save #myname[Israel]? Behold, my clan is the #underline[weakest] in #myhl(placesColor)[Manasseh], and I am the #underline[least] in my father’s house.” 
#versenum(16) And the #myhl(divineColor)[#smallcaps[Lord]] said to him, “But I will be with you, and you shall strike the #myname[Midianites] as one man.” 
#versenum(17) And he said to him, “If now I have found favor in your eyes, then show me a sign that it is you who speak with me. 
#versenum(18) Please do not depart from here until I come to you and bring out my #underline[present] and set it before you.” And he said, “I will stay till you return.”


  
#versenum(19) So #myhl(menColor)[Gideon] went into his house and prepared a young goat and unleavened cakes from an ephah#footnote[Judges 6:19 An #emph[ephah] was about 3/5 bushel or 22 liters] of #underline[flour]. The meat he put in a #underline[basket], and the broth he put in a #underline[pot], and brought them to him under the terebinth and presented them. 
#versenum(20) And the angel of #myhl(divineColor)[God] said to him, “Take the meat and the unleavened cakes, and put them on this rock, and #underline[pour] the broth over them.” And he did so. 
#versenum(21) Then the #myhl(divineColor)[angel of the #smallcaps[Lord]] reached out the #underline[tip] of the staff that was in his hand and #underline[touched] the meat and the unleavened cakes. And fire #underline[sprang] up from the rock and #underline[consumed] the meat and the unleavened cakes. And the #myhl(divineColor)[angel of the #smallcaps[Lord]] #underline[vanished] from his sight. 
#versenum(22) Then #myhl(menColor)[Gideon] #underline[perceived] that he was the #myhl(divineColor)[angel of the #smallcaps[Lord]]. And #myhl(menColor)[Gideon] said, “Alas, O #myhl(divineColor)[Lord] #myhl(divineColor)[GOD]! For now I have seen the #myhl(divineColor)[angel of the #smallcaps[Lord]] face to face.” 
#versenum(23) But the #myhl(divineColor)[#smallcaps[Lord]] said to him, “Peace be to you. Do not fear; you shall not die.” 
#versenum(24) Then #myhl(menColor)[Gideon] built an altar there to the #myhl(divineColor)[#smallcaps[Lord]] and called it, The #myhl(divineColor)[#smallcaps[Lord]] Is Peace. To this day it still stands at #myhl(placesColor)[Ophrah], which belongs to the #myname[Abiezrites].


  
#versenum(25) That night the #myhl(divineColor)[#smallcaps[Lord]] said to him, “Take your father’s bull, and the #mynumber[second] bull #mynumber[seven] years old, and pull down the altar of #myname[Baal] that your father has, and cut down the #myname[Asherah] that is beside it 
#versenum(26) and build an altar to the #myhl(divineColor)[#smallcaps[Lord] your God] on the top of the stronghold here, with stones laid in #underline[due] order. Then take the #mynumber[second] bull and offer it as a burnt offering with the wood of the #myname[Asherah] that you shall cut down.” 
#versenum(27) So #myhl(menColor)[Gideon] took #mynumber[ten] men of his servants and did as the #myhl(divineColor)[#smallcaps[Lord]] had told him. But because he was too afraid of his family and the men of the town to do it by day, he did it by night.


  
#section-heading[Gideon Destroys the Altar of Baal]


#versenum(28) When the men of the town rose early in the morning, behold, the altar of #myname[Baal] was broken down, and the #myname[Asherah] beside it was cut down, and the #mynumber[second] bull was offered on the altar that had been built. 
#versenum(29) And they said to one another, “Who has done this thing?” And after they had searched and inquired, they said, “#myhl(menColor)[Gideon] the son of #myhl(menColor)[Joash] has done this thing.” 
#versenum(30) Then the men of the town said to #myhl(menColor)[Joash], “Bring out your son, that he may die, for he has broken down the altar of #myname[Baal] and cut down the #myname[Asherah] beside it.” 
#versenum(31) But #myhl(menColor)[Joash] said to all who stood against him, “Will you contend for #myname[Baal]? Or will you save him? Whoever #underline[contends] for him shall be put to death by morning. If he is a god, let him contend for himself, because his altar has been broken down.” 
#versenum(32) Therefore on that day #myhl(menColor)[Gideon]#footnote[Judges 6:32 Hebrew #emph[he]] was called #myhl(menColor)[Jerubbaal], that is to say, “Let #myname[Baal] contend against him,” because he broke down his altar.


  
#versenum(33) Now all the #myname[Midianites] and the #myname[Amalekites] and the people of the East came together, and they crossed the #myhl(placesColor)[Jordan] and encamped in the #myhl(placesColor)[Valley of Jezreel]. 
#versenum(34) But the #myhl(divineColor)[Spirit] of the #myhl(divineColor)[#smallcaps[Lord]] #underline[clothed] #myhl(menColor)[Gideon], and he sounded the trumpet, and the #myname[Abiezrites] were called out to follow him. 
#versenum(35) And he sent messengers throughout all #myname[Manasseh], and they too were called out to follow him. And he sent messengers to #myname[Asher], #myname[Zebulun], and #myname[Naphtali], and they went up to meet them.


  
#section-heading[The Sign of the Fleece]


#versenum(36) Then #myhl(menColor)[Gideon] said to #myhl(divineColor)[God], “If you will save #myname[Israel] by my hand, as you have said, 
#versenum(37) behold, I am #underline[laying] a fleece of #underline[wool] on the threshing floor. If there is dew on the fleece alone, and it is dry on all the ground, then I shall know that you will save #myname[Israel] by my hand, as you have said.” 
#versenum(38) And it was so. When he rose early next morning and #underline[squeezed] the fleece, he #underline[wrung] enough dew from the fleece to #underline[fill] a bowl with water. 
#versenum(39) Then #myhl(menColor)[Gideon] said to #myhl(divineColor)[God], “Let not your anger burn against me; let me speak just once more. Please let me test just once more with the fleece. Please let it be dry on the fleece only, and on all the ground let there be dew.” 
#versenum(40) And #myhl(divineColor)[God] did so that night; and it was dry on the fleece only, and on all the ground there was dew.


  
#chapter-heading[Judges 7]


#section-heading[Gideon’s Three Hundred Men]


#versenum(1) Then #myhl(menColor)[Jerubbaal] (that is, #myhl(menColor)[Gideon]) and all the people who were with him rose early and encamped beside the spring of #underline[#myhl(placesColor)[Harod]]. And #myhl(placesColor)[the camp of Midian] was north of them, by the hill of #underline[#myhl(placesColor)[Moreh]], in the valley.


  
#versenum(2) The #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Gideon], “The people with you are too many for me to give the #myname[Midianites] into their hand, lest #myname[Israel] #underline[boast] over me, saying, ‘My own hand has saved me.’ 
#versenum(3) Now therefore #underline[proclaim] in the ears of the people, saying, ‘Whoever is #underline[fearful] and #underline[trembling], let him return home and hurry away from #myhl(placesColor)[Mount Gilead].’” Then #mynumber[22,000] of the people returned, and #mynumber[10,000] remained.


  
#versenum(4) And the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Gideon], “The people are still too many. Take them down to the water, and I will test them for you there, and anyone of whom I say to you, ‘This one shall go with you,’ shall go with you, and anyone of whom I say to you, ‘This one shall not go with you,’ shall not go.” 
#versenum(5) So he brought the people down to the water. And the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Gideon], “Every one who laps the water with his tongue, as a #underline[dog] laps, you shall set by himself. Likewise, every one who #underline[kneels] down to drink.” 
#versenum(6) And the number of those who lapped, putting their hands to their #underline[mouths], was #mynumber[300] men, but all the rest of the people #underline[knelt] down to drink water. 
#versenum(7) And the #myhl(divineColor)[#smallcaps[Lord]] said to #myhl(menColor)[Gideon], “With the #mynumber[300] men who lapped I will save you and give the #myname[Midianites] into your hand, and let all the others go every man to his home.” 
#versenum(8) So the people took provisions in their hands, and their trumpets. And he sent all the rest of #myname[Israel] every man to his tent, but #underline[retained] the #mynumber[300] men. And #myhl(placesColor)[the camp of Midian] was below him in the valley.


  
#versenum(9) That same night the #myhl(divineColor)[#smallcaps[Lord]] said to him, “Arise, go down against the camp, for I have given it into your hand. 
#versenum(10) But if you are afraid to go down, go down to the camp with #myhl(menColor)[Purah] your servant. 
#versenum(11) And you shall hear what they say, and afterward your hands shall be strengthened to go down against the camp.” Then he went down with #myhl(menColor)[Purah] his servant to the #underline[outposts] of the armed men who were in the camp. 
#versenum(12) And the #myname[Midianites] and the #myname[Amalekites] and all the people of the East lay along the valley like locusts in abundance, and their camels were without number, as the sand that is on the seashore in abundance. 
#versenum(13) When #myhl(menColor)[Gideon] came, behold, a man was telling a dream to his comrade. And he said, “Behold, I #underline[dreamed] a dream, and behold, a #underline[cake] of barley bread #underline[tumbled] into #myhl(placesColor)[the camp of Midian] and came to the tent and struck it so that it fell and turned it #underline[upside] down, so that the tent lay flat.” 
#versenum(14) And his comrade answered, “This is no other than the sword of #myhl(menColor)[Gideon] the son of #myhl(menColor)[Joash], a man of #myname[Israel]; #myhl(divineColor)[God] has given into his hand #myname[Midian] and all the camp.”


  
#versenum(15) As soon as #myhl(menColor)[Gideon] heard the telling of the dream and its #underline[interpretation], he worshiped. And he returned to #myhl(placesColor)[the camp of Israel] and said, “Arise, for the #myhl(divineColor)[#smallcaps[Lord]] has given the #underline[host] of #myname[Midian] into your hand.” 
#versenum(16) And he divided the #mynumber[300] men into #mynumber[three] companies and put trumpets into the hands of all of them and empty jars, with torches inside the jars. 
#versenum(17) And he said to them, “Look at me, and do likewise. When I come to the outskirts of the camp, do as I do. 
#versenum(18) When I blow the trumpet, I and all who are with me, then blow the trumpets also on every side of all the camp and shout, ‘For the #myhl(divineColor)[#smallcaps[Lord]] and for #myhl(menColor)[Gideon].’”


  
#section-heading[Gideon Defeats Midian]


#versenum(19) So #myhl(menColor)[Gideon] and the #mynumber[hundred] men who were with him came to the outskirts of the camp at the beginning of the middle watch, when they had just set the watch. And they blew the trumpets and #underline[smashed] the jars that were in their hands. 
#versenum(20) Then the #mynumber[three] companies blew the trumpets and broke the jars. They held in their left hands the torches, and in their right hands the trumpets to blow. And they cried out, “A sword for the #myhl(divineColor)[#smallcaps[Lord]] and for #myhl(menColor)[Gideon]!” 
#versenum(21) Every man stood in his place around the camp, and all the army ran. They cried out and fled. 
#versenum(22) When they blew the #mynumber[300] trumpets, the #myhl(divineColor)[#smallcaps[Lord]] set every man’s sword against his comrade and against all the army. And the army fled as far as #underline[#myhl(placesColor)[Beth-shittah]] toward #underline[#myhl(placesColor)[Zererah]],#footnote[Judges 7:22 Some Hebrew manuscripts #emph[Zeredah]] as far as the border of #underline[#myhl(placesColor)[Abel-meholah]], by #underline[#myhl(placesColor)[Tabbath]]. 
#versenum(23) And the men of #myname[Israel] were called out from #myname[Naphtali] and from #myname[Asher] and from all #myname[Manasseh], and they pursued after #myname[Midian].


  
#versenum(24) #myhl(menColor)[Gideon] sent messengers throughout all #myhl(placesColor)[the hill country of Ephraim], saying, “Come down against the #myname[Midianites] and #underline[capture] the waters against them, as far as #myhl(placesColor)[Beth-barah], and also the #myhl(placesColor)[Jordan].” So all the men of #myname[Ephraim] were called out, and they captured the waters as far as #myhl(placesColor)[Beth-barah], and also the #myhl(placesColor)[Jordan]. 
#versenum(25) And they captured the #mynumber[two] princes of #myname[Midian], #myhl(menColor)[Oreb] and #myhl(menColor)[Zeeb]. They killed #myhl(menColor)[Oreb] at the #myhl(placesColor)[rock of Oreb], and #myhl(menColor)[Zeeb] they killed at the #myhl(placesColor)[winepress of Zeeb]. Then they pursued #myname[Midian], and they brought the heads of #myhl(menColor)[Oreb] and #myhl(menColor)[Zeeb] to #myhl(menColor)[Gideon] #underline[across] the #myhl(placesColor)[Jordan].


  
#chapter-heading[Judges 8]


#section-heading[Gideon Defeats Zebah and Zalmunna]


#versenum(1) Then the men of #myname[Ephraim] said to him, “What is this that you have done to us, not to call us when you went to fight against #myname[Midian]?” And they #underline[accused] him #underline[fiercely]. 
#versenum(2) And he said to them, “What have I done now in comparison with you? Is not the gleaning of the grapes of #myname[Ephraim] better than the #underline[grape] harvest of #myhl(menColor)[Abiezer]? 
#versenum(3) #myhl(divineColor)[God] has given into your hands the princes of #myname[Midian], #myhl(menColor)[Oreb] and #myhl(menColor)[Zeeb]. What have I been able to do in comparison with you?” Then their anger#footnote[Judges 8:3 Hebrew #emph[their spirit]] against him #underline[subsided] when he said this.


  
#versenum(4) And #myhl(menColor)[Gideon] came to the #myhl(placesColor)[Jordan] and crossed over, he and the #mynumber[300] men who were with him, exhausted yet pursuing. 
#versenum(5) So he said to the men of #myhl(placesColor)[Succoth], “Please give #underline[loaves] of bread to the people who follow me, for they are exhausted, and I am pursuing after #myhl(menColor)[Zebah] and #myhl(menColor)[Zalmunna], the kings of #myname[Midian].” 
#versenum(6) And the officials of #myhl(placesColor)[Succoth] said, “Are the hands of #myhl(menColor)[Zebah] and #myhl(menColor)[Zalmunna] already in your hand, that we should give bread to your army?” 
#versenum(7) So #myhl(menColor)[Gideon] said, “Well then, when the #myhl(divineColor)[#smallcaps[Lord]] has given #myhl(menColor)[Zebah] and #myhl(menColor)[Zalmunna] into my hand, I will #underline[flail] your flesh with the thorns of the wilderness and with briers.” 
#versenum(8) And from there he went up to #myhl(placesColor)[Penuel], and spoke to them in the same way, and the men of #myhl(placesColor)[Penuel] answered him as the men of #myhl(placesColor)[Succoth] had answered. 
#versenum(9) And he said to the men of #myhl(placesColor)[Penuel], “When I come again in peace, I will break down this tower.”


  
#versenum(10) Now #myhl(menColor)[Zebah] and #myhl(menColor)[Zalmunna] were in #underline[#myhl(placesColor)[Karkor]] with their army, about #underline[#mynumber[15,000]] men, all who were left of all the army of the people of the East, for there had fallen #underline[#mynumber[120,000]] men who drew the sword. 
#versenum(11) And #myhl(menColor)[Gideon] went up by the way of the tent #underline[dwellers] east of #underline[#myhl(placesColor)[Nobah]] and #underline[#myhl(placesColor)[Jogbehah]] and #underline[attacked] the army, for the army #underline[felt] #underline[secure]. 
#versenum(12) And #myhl(menColor)[Zebah] and #myhl(menColor)[Zalmunna] fled, and he pursued them and captured the #mynumber[two] kings of #myname[Midian], #myhl(menColor)[Zebah] and #myhl(menColor)[Zalmunna], and he threw all the army into a panic.


  
#versenum(13) Then #myhl(menColor)[Gideon] the son of #myhl(menColor)[Joash] returned from the battle by the ascent of #myhl(placesColor)[Heres]. 
#versenum(14) And he captured a young man of #myhl(placesColor)[Succoth] and #underline[questioned] him. And he wrote down for him the officials and elders of #myhl(placesColor)[Succoth], #underline[#mynumber[seventy-seven]] men. 
#versenum(15) And he came to the men of #myhl(placesColor)[Succoth] and said, “Behold #myhl(menColor)[Zebah] and #myhl(menColor)[Zalmunna], about whom you #underline[taunted] me, saying, ‘Are the hands of #myhl(menColor)[Zebah] and #myhl(menColor)[Zalmunna] already in your hand, that we should give bread to your men who are exhausted?’” 
#versenum(16) And he took the elders of the city, and he took thorns of the wilderness and briers and with them #underline[taught] the men of #myhl(placesColor)[Succoth] a #underline[lesson]. 
#versenum(17) And he broke down the tower of #myhl(placesColor)[Penuel] and killed the men of the city.


  
#versenum(18) Then he said to #myhl(menColor)[Zebah] and #myhl(menColor)[Zalmunna], “Where are the men whom you killed at #myhl(placesColor)[Tabor]?” They answered, “As you are, so were they. Every one of them #underline[resembled] the son of a king.” 
#versenum(19) And he said, “They were my brothers, the sons of my mother. As the #myhl(divineColor)[#smallcaps[Lord]] lives, if you had saved them alive, I would not kill you.” 
#versenum(20) So he said to #underline[#myhl(menColor)[Jether]] his firstborn, “Rise and kill them!” But the young man did not draw his sword, for he was afraid, because he was still a young man. 
#versenum(21) Then #myhl(menColor)[Zebah] and #myhl(menColor)[Zalmunna] said, “Rise yourself and fall upon us, for as the man is, so is his strength.” And #myhl(menColor)[Gideon] arose and killed #myhl(menColor)[Zebah] and #myhl(menColor)[Zalmunna], and he took the crescent ornaments that were on the necks of their camels.


  
#section-heading[Gideon’s Ephod]


#versenum(22) Then the men of #myname[Israel] said to #myhl(menColor)[Gideon], “Rule over us, you and your son and your #underline[grandson] also, for you have saved us from the hand of #myname[Midian].” 
#versenum(23) #myhl(menColor)[Gideon] said to them, “I will not rule over you, and my son will not rule over you; the #myhl(divineColor)[#smallcaps[Lord]] will rule over you.” 
#versenum(24) And #myhl(menColor)[Gideon] said to them, “Let me make a #underline[request] of you: every one of you give me the earrings from his spoil.” (For they had golden earrings, because they were #underline[#myname[Ishmaelites]].) 
#versenum(25) And they answered, “We will willingly give them.” And they spread a cloak, and every man threw in it the earrings of his spoil. 
#versenum(26) And the weight of the golden earrings that he #underline[requested] was #underline[#mynumber[1,700]] shekels#footnote[Judges 8:26 A #emph[shekel] was about 2/5 ounce or 11 grams] of gold, besides the crescent ornaments and the #underline[pendants] and the #underline[purple] garments worn by the kings of #myname[Midian], and besides the #underline[collars] that were around the necks of their camels. 
#versenum(27) And #myhl(menColor)[Gideon] made an ephod of it and put it in his city, in #myhl(placesColor)[Ophrah]. And all #myname[Israel] whored after it there, and it became a snare to #myhl(menColor)[Gideon] and to his family. 
#versenum(28) So #myname[Midian] was subdued before the people of #myname[Israel], and they raised their heads no more. And the land had rest for #mynumber[forty] years in the days of #myhl(menColor)[Gideon].


  
#section-heading[The Death of Gideon]


#versenum(29) #myhl(menColor)[Jerubbaal] the son of #myhl(menColor)[Joash] went and lived in his own house. 
#versenum(30) Now #myhl(menColor)[Gideon] had #mynumber[seventy] sons, his own offspring,#footnote[Judges 8:30 Hebrew #emph[who came from his own loins]] for he had many wives. 
#versenum(31) And his concubine who was in #myhl(placesColor)[Shechem] also bore him a son, and he called his name #myhl(menColor)[Abimelech]. 
#versenum(32) And #myhl(menColor)[Gideon] the son of #myhl(menColor)[Joash] died in a good old age and was buried in the tomb of #myhl(menColor)[Joash] his father, at #myhl(placesColor)[Ophrah] of the #myname[Abiezrites].


  
#versenum(33) As soon as #myhl(menColor)[Gideon] died, the people of #myname[Israel] turned again and whored after the #myname[Baals] and made #myname[Baal-berith] their god. 
#versenum(34) And the people of #myname[Israel] did not remember the #myhl(divineColor)[#smallcaps[Lord]] their #myhl(divineColor)[God], who had delivered them from the hand of all their enemies on every side, 
#versenum(35) and they did not show #underline[steadfast] love to the family of #myhl(menColor)[Jerubbaal] (that is, #myhl(menColor)[Gideon]) in return for all the good that he had done to #myname[Israel].


  
#chapter-heading[Judges 9]


#section-heading[Abimelech’s Conspiracy]


#versenum(1) Now #myhl(menColor)[Abimelech] the son of #myhl(menColor)[Jerubbaal] went to #myhl(placesColor)[Shechem] to his mother’s relatives and said to them and to the whole clan of his mother’s family, 
#versenum(2) “Say in the ears of all the leaders of #myhl(placesColor)[Shechem], ‘Which is better for you, that all #mynumber[seventy] of the sons of #myhl(menColor)[Jerubbaal] rule over you, or that one rule over you?’ Remember also that I am your #underline[bone] and your flesh.”


  
#versenum(3) And his mother’s relatives spoke all these words on his #underline[behalf] in the ears of all the leaders of #myhl(placesColor)[Shechem], and their hearts #underline[inclined] to follow #myhl(menColor)[Abimelech], for they said, “He is our brother.” 
#versenum(4) And they gave him #mynumber[seventy] pieces of silver out of the house of #myname[Baal-berith] with which #myhl(menColor)[Abimelech] hired worthless and #underline[reckless] fellows, who followed him. 
#versenum(5) And he went to his father’s house at #myhl(placesColor)[Ophrah] and killed his brothers the sons of #myhl(menColor)[Jerubbaal], #mynumber[seventy] men, on one stone. But #myhl(menColor)[Jotham] the youngest son of #myhl(menColor)[Jerubbaal] was left, for he hid himself. 
#versenum(6) And all the leaders of #myhl(placesColor)[Shechem] came together, and all #myhl(placesColor)[Beth-millo], and they went and made #myhl(menColor)[Abimelech] king, by the oak of the #underline[pillar] at #myhl(placesColor)[Shechem].


  
#versenum(7) When it was told to #myhl(menColor)[Jotham], he went and stood on top of #myhl(placesColor)[Mount Gerizim] and cried #underline[aloud] and said to them, “Listen to me, you leaders of #myhl(placesColor)[Shechem], that #myhl(divineColor)[God] may listen to you. 
#versenum(8) The trees once went out to anoint a king over them, and they said to the olive tree, ‘Reign over us.’ 
#versenum(9) But the olive tree said to them, ‘Shall I leave my abundance, by which gods and men are #underline[honored], and go hold sway over the trees?’ 
#versenum(10) And the trees said to the fig tree, ‘You come and reign over us.’ 
#versenum(11) But the fig tree said to them, ‘Shall I leave my #underline[sweetness] and my good fruit and go hold sway over the trees?’ 
#versenum(12) And the trees said to the vine, ‘You come and reign over us.’ 
#versenum(13) But the vine said to them, ‘Shall I leave my wine that #underline[cheers] #myhl(divineColor)[God] and men and go hold sway over the trees?’ 
#versenum(14) Then all the trees said to the bramble, ‘You come and reign over us.’ 
#versenum(15) And the bramble said to the trees, ‘If in good faith you are #underline[anointing] me king over you, then come and take refuge in my #underline[shade], but if not, let fire come out of the bramble and devour the #underline[cedars] of #myhl(placesColor)[Lebanon].’


  
#versenum(16) “Now therefore, if you acted in good faith and integrity when you made #myhl(menColor)[Abimelech] king, and if you have dealt well with #myhl(menColor)[Jerubbaal] and his house and have done to him as his deeds #underline[deserved]—
#versenum(17) for my father fought for you and risked his life and delivered you from the hand of #myname[Midian], 
#versenum(18) and you have #underline[risen] up against my father’s house this day and have killed his sons, #mynumber[seventy] men on one stone, and have made #myhl(menColor)[Abimelech], the son of his female servant, king over the leaders of #myhl(placesColor)[Shechem], because he is your relative—
#versenum(19) if you then have acted in good faith and integrity with #myhl(menColor)[Jerubbaal] and with his house this day, then rejoice in #myhl(menColor)[Abimelech], and let him also rejoice in you. 
#versenum(20) But if not, let fire come out from #myhl(menColor)[Abimelech] and devour the leaders of #myhl(placesColor)[Shechem] and #myhl(placesColor)[Beth-millo]; and let fire come out from the leaders of #myhl(placesColor)[Shechem] and from #myhl(placesColor)[Beth-millo] and devour #myhl(menColor)[Abimelech].” 
#versenum(21) And #myhl(menColor)[Jotham] ran away and fled and went to #underline[#myhl(placesColor)[Beer]] and lived there, because of #myhl(menColor)[Abimelech] his brother.


  
#section-heading[The Downfall of Abimelech]


#versenum(22) #myhl(menColor)[Abimelech] ruled over #myname[Israel] #mynumber[three] years. 
#versenum(23) And #myhl(divineColor)[God] sent an evil spirit between #myhl(menColor)[Abimelech] and the leaders of #myhl(placesColor)[Shechem], and the leaders of #myhl(placesColor)[Shechem] dealt #underline[treacherously] with #myhl(menColor)[Abimelech], 
#versenum(24) that the #underline[violence] done to the #mynumber[seventy] sons of #myhl(menColor)[Jerubbaal] might come, and their blood be laid on #myhl(menColor)[Abimelech] their brother, who killed them, and on the men of #myhl(placesColor)[Shechem], who strengthened his hands to kill his brothers. 
#versenum(25) And the leaders of #myhl(placesColor)[Shechem] put men in ambush against him on the mountaintops, and they #underline[robbed] all who passed by them along that way. And it was told to #myhl(menColor)[Abimelech].


  
#versenum(26) And #myhl(menColor)[Gaal] the son of #myhl(menColor)[Ebed] moved into #myhl(placesColor)[Shechem] with his relatives, and the leaders of #myhl(placesColor)[Shechem] put #underline[confidence] in him. 
#versenum(27) And they went out into the field and gathered the grapes from their vineyards and trod them and held a #underline[festival]; and they went into the house of their god and ate and drank and #underline[reviled] #myhl(menColor)[Abimelech]. 
#versenum(28) And #myhl(menColor)[Gaal] the son of #myhl(menColor)[Ebed] said, “Who is #myhl(menColor)[Abimelech], and who are we of #myhl(placesColor)[Shechem], that we should serve him? Is he not the son of #myhl(menColor)[Jerubbaal], and is not #myhl(menColor)[Zebul] his #underline[officer]? Serve the men of #myhl(menColor)[Hamor] the father of #myhl(placesColor)[Shechem]; but why should we serve him? 
#versenum(29) Would that this people were under my hand! Then I would #underline[remove] #myhl(menColor)[Abimelech]. I would say#footnote[Judges 9:29 Septuagint; Hebrew #emph[and he said]] to #myhl(menColor)[Abimelech], ‘#underline[Increase] your army, and come out.’”


  
#versenum(30) When #myhl(menColor)[Zebul] the #underline[ruler] of the city heard the words of #myhl(menColor)[Gaal] the son of #myhl(menColor)[Ebed], his anger was kindled. 
#versenum(31) And he sent messengers to #myhl(menColor)[Abimelech] secretly,#footnote[Judges 9:31 Or #emph[at Tormah]] saying, “Behold, #myhl(menColor)[Gaal] the son of #myhl(menColor)[Ebed] and his relatives have come to #myhl(placesColor)[Shechem], and they are #underline[stirring] up#footnote[Judges 9:31 Hebrew #emph[besieging], or #emph[closing up]] the city against you. 
#versenum(32) Now therefore, go by night, you and the people who are with you, and set an ambush in the field. 
#versenum(33) Then in the morning, as soon as the sun is up, rise early and #underline[rush] upon the city. And when he and the people who are with him come out against you, you may do to them as your hand #underline[finds] to do.”


  
#versenum(34) So #myhl(menColor)[Abimelech] and all the men who were with him rose up by night and set an ambush against #myhl(placesColor)[Shechem] in #mynumber[four] companies. 
#versenum(35) And #myhl(menColor)[Gaal] the son of #myhl(menColor)[Ebed] went out and stood in the entrance of the gate of the city, and #myhl(menColor)[Abimelech] and the people who were with him rose from the ambush. 
#versenum(36) And when #myhl(menColor)[Gaal] saw the people, he said to #myhl(menColor)[Zebul], “Look, people are coming down from the mountaintops!” And #myhl(menColor)[Zebul] said to him, “You #underline[mistake]#footnote[Judges 9:36 Hebrew #emph[You see]] the #underline[shadow] of the mountains for men.” 
#versenum(37) #myhl(menColor)[Gaal] spoke again and said, “Look, people are coming down from the #underline[center] of the land, and one company is coming from the direction of the #underline[#myhl(placesColor)[Diviners]’ Oak].” 
#versenum(38) Then #myhl(menColor)[Zebul] said to him, “Where is your mouth now, you who said, ‘Who is #myhl(menColor)[Abimelech], that we should serve him?’ Are not these the people whom you #underline[despised]? Go out now and fight with them.” 
#versenum(39) And #myhl(menColor)[Gaal] went out at the head of the leaders of #myhl(placesColor)[Shechem] and fought with #myhl(menColor)[Abimelech]. 
#versenum(40) And #myhl(menColor)[Abimelech] chased him, and he fled before him. And many fell #underline[wounded], up to the entrance of the gate. 
#versenum(41) And #myhl(menColor)[Abimelech] lived at #underline[#myhl(placesColor)[Arumah]], and #myhl(menColor)[Zebul] drove out #myhl(menColor)[Gaal] and his relatives, so that they could not dwell at #myhl(placesColor)[Shechem].


  
#versenum(42) On the following day, the people went out into the field, and #myhl(menColor)[Abimelech] was told. 
#versenum(43) He took his people and divided them into #mynumber[three] companies and set an ambush in the fields. And he looked and saw the people coming out of the city. So he rose against them and killed them. 
#versenum(44) #myhl(menColor)[Abimelech] and the company that was with him rushed forward and stood at the entrance of the gate of the city, while the #mynumber[two] companies rushed upon all who were in the field and killed them. 
#versenum(45) And #myhl(menColor)[Abimelech] fought against the city all that day. He captured the city and killed the people who were in it, and he #underline[razed] the city and #underline[sowed] it with salt.


  
#versenum(46) When all the leaders of the Tower of #myhl(placesColor)[Shechem] heard of it, they entered the stronghold of the house of #underline[#myname[El-berith]]. 
#versenum(47) #myhl(menColor)[Abimelech] was told that all the leaders of the Tower of #myhl(placesColor)[Shechem] were gathered together. 
#versenum(48) And #myhl(menColor)[Abimelech] went up to #myhl(placesColor)[Mount #underline[Zalmon]], he and all the people who were with him. And #myhl(menColor)[Abimelech] took an #underline[axe] in his hand and cut down a bundle of #underline[brushwood] and took it up and laid it on his shoulder. And he said to the men who were with him, “What you have seen me do, hurry and do as I have done.” 
#versenum(49) So every one of the people cut down his bundle and following #myhl(menColor)[Abimelech] put it against the stronghold, and they set the stronghold on fire over them, so that all the people of the Tower of #myhl(placesColor)[Shechem] also died, about #mynumber[1,000] men and women.


  
#versenum(50) Then #myhl(menColor)[Abimelech] went to #myhl(placesColor)[Thebez] and encamped against #myhl(placesColor)[Thebez] and captured it. 
#versenum(51) But there was a strong tower within the city, and all the men and women and all the leaders of the city fled to it and shut themselves in, and they went up to the roof of the tower. 
#versenum(52) And #myhl(menColor)[Abimelech] came to the tower and fought against it and drew near to the door of the tower to burn it with fire. 
#versenum(53) And a certain woman threw an upper #underline[millstone] on #underline[#myhl(menColor)[Abimelech]’s] head and crushed his #underline[skull]. 
#versenum(54) Then he called quickly to the young man his #underline[armor-bearer] and said to him, “Draw your sword and kill me, lest they say of me, ‘A woman killed him.’” And his young man thrust him through, and he died. 
#versenum(55) And when the men of #myname[Israel] saw that #myhl(menColor)[Abimelech] was dead, everyone departed to his home. 
#versenum(56) Thus #myhl(divineColor)[God] returned the evil of #myhl(menColor)[Abimelech], which he committed against his father in killing his #mynumber[seventy] brothers. 
#versenum(57) And #myhl(divineColor)[God] also made all the evil of the men of #myhl(placesColor)[Shechem] return on their heads, and upon them came the curse of #myhl(menColor)[Jotham] the son of #myhl(menColor)[Jerubbaal].


  
#chapter-heading[Judges 10]


#section-heading[Tola and Jair]


#versenum(1) After #myhl(menColor)[Abimelech] there arose to save #myname[Israel] #underline[#myhl(menColor)[Tola]] the son of #underline[#myhl(menColor)[Puah]], son of #underline[#myhl(menColor)[Dodo]], a man of #myname[Issachar], and he lived at #myhl(placesColor)[Shamir] in #myhl(placesColor)[the hill country of Ephraim]. 
#versenum(2) And he judged #myname[Israel] #underline[#mynumber[twenty-three]] years. Then he died and was buried at #myhl(placesColor)[Shamir].


  
#versenum(3) After him arose #myhl(menColor)[Jair] the #myname[Gileadite], who judged #myname[Israel] #mynumber[twenty-two] years. 
#versenum(4) And he had #mynumber[thirty] sons who rode on #mynumber[thirty] donkeys, and they had #mynumber[thirty] cities, called #underline[#myhl(placesColor)[Havvoth-jair]] to this day, which are in the land of #myhl(placesColor)[Gilead]. 
#versenum(5) And #myhl(menColor)[Jair] died and was buried in #underline[#myhl(placesColor)[Kamon]].


  
#section-heading[Further Disobedience and Oppression]


#versenum(6) The people of #myname[Israel] again did what was evil in the sight of the #myhl(divineColor)[#smallcaps[Lord]] and served the #myname[Baals] and the #myname[Ashtaroth], the gods of #underline[#myhl(placesColor)[Syria]], the gods of #myhl(placesColor)[Sidon], the gods of #myhl(placesColor)[Moab], the gods of the #myname[Ammonites], and the gods of the #myname[Philistines]. And they #underline[forsook] the #myhl(divineColor)[#smallcaps[Lord]] and did not serve him. 
#versenum(7) So the anger of the #myhl(divineColor)[#smallcaps[Lord]] was kindled against #myname[Israel], and he sold them into the hand of the #myname[Philistines] and into the hand of the #myname[Ammonites], 
#versenum(8) and they crushed and oppressed the people of #myname[Israel] that year. For #mynumber[eighteen] years they oppressed all the people of #myname[Israel] who were beyond the #myhl(placesColor)[Jordan] in the land of the #myname[Amorites], which is in #myhl(placesColor)[Gilead]. 
#versenum(9) And the #myname[Ammonites] crossed the #myhl(placesColor)[Jordan] to fight also against #myname[Judah] and against #myname[Benjamin] and against the house of #myname[Ephraim], so that #myname[Israel] was #underline[severely] #underline[distressed].


  
#versenum(10) And the people of #myname[Israel] cried out to the #myhl(divineColor)[#smallcaps[Lord]], saying, “We have sinned against you, because we have forsaken our #myhl(divineColor)[God] and have served the #myname[Baals].” 
#versenum(11) And the #myhl(divineColor)[#smallcaps[Lord]] said to the people of #myname[Israel], “Did I not save you from the #myname[Egyptians] and from the #myname[Amorites], from the #myname[Ammonites] and from the #myname[Philistines]? 
#versenum(12) The #myname[Sidonians] also, and the #myname[Amalekites] and the #underline[#myname[Maonites]] oppressed you, and you cried out to me, and I saved you out of their hand. 
#versenum(13) Yet you have forsaken me and served other gods; therefore I will save you no more. 
#versenum(14) Go and #underline[cry] out to the gods whom you have chosen; let them save you in the time of your distress.” 
#versenum(15) And the people of #myname[Israel] said to the #myhl(divineColor)[#smallcaps[Lord]], “We have sinned; do to us whatever seems good to you. Only please deliver us this day.” 
#versenum(16) So they put away the foreign gods from among them and served the #myhl(divineColor)[#smallcaps[Lord]], and he became #underline[impatient] over the #underline[misery] of #myname[Israel].


  
#versenum(17) Then the #myname[Ammonites] were called to arms, and they encamped in #myhl(placesColor)[Gilead]. And the people of #myname[Israel] came together, and they encamped at #myhl(placesColor)[Mizpah]. 
#versenum(18) And the people, the leaders of #myhl(placesColor)[Gilead], said one to another, “Who is the man who will begin to fight against the #myname[Ammonites]? He shall be head over all the inhabitants of #myhl(placesColor)[Gilead].”


  
#chapter-heading[Judges 11]


#section-heading[Jephthah Delivers Israel]


#versenum(1) Now #myhl(menColor)[Jephthah] the #myname[Gileadite] was a mighty #underline[warrior], but he was the son of a prostitute. #myhl(placesColor)[Gilead] was the father of #myhl(menColor)[Jephthah]. 
#versenum(2) And #underline[#myhl(placesColor)[Gilead]’s] wife also bore him sons. And when his #underline[wife’s] sons grew up, they drove #myhl(menColor)[Jephthah] out and said to him, “You shall not have an inheritance in our father’s house, for you are the son of another woman.” 
#versenum(3) Then #myhl(menColor)[Jephthah] fled from his brothers and lived in the land of #myname[Tob], and worthless fellows #underline[collected] around #myhl(menColor)[Jephthah] and went out with him.


  
#versenum(4) After a time the #myname[Ammonites] made war against #myname[Israel]. 
#versenum(5) And when the #myname[Ammonites] made war against #myname[Israel], the elders of #myhl(placesColor)[Gilead] went to bring #myhl(menColor)[Jephthah] from the land of #myname[Tob]. 
#versenum(6) And they said to #myhl(menColor)[Jephthah], “Come and be our leader, that we may fight against the #myname[Ammonites].” 
#versenum(7) But #myhl(menColor)[Jephthah] said to the elders of #myhl(placesColor)[Gilead], “Did you not hate me and drive me out of my father’s house? Why have you come to me now when you are in distress?” 
#versenum(8) And the elders of #myhl(placesColor)[Gilead] said to #myhl(menColor)[Jephthah], “That is why we have turned to you now, that you may go with us and fight against the #myname[Ammonites] and be our head over all the inhabitants of #myhl(placesColor)[Gilead].” 
#versenum(9) #myhl(menColor)[Jephthah] said to the elders of #myhl(placesColor)[Gilead], “If you bring me home again to fight against the #myname[Ammonites], and the #myhl(divineColor)[#smallcaps[Lord]] gives them over to me, I will be your head.” 
#versenum(10) And the elders of #myhl(placesColor)[Gilead] said to #myhl(menColor)[Jephthah], “The #myhl(divineColor)[#smallcaps[Lord]] will be witness between us, if we do not do as you say.” 
#versenum(11) So #myhl(menColor)[Jephthah] went with the elders of #myhl(placesColor)[Gilead], and the people made him head and leader over them. And #myhl(menColor)[Jephthah] spoke all his words before the #myhl(divineColor)[#smallcaps[Lord]] at #myhl(placesColor)[Mizpah].


  
#versenum(12) Then #myhl(menColor)[Jephthah] sent messengers to the king of the #myname[Ammonites] and said, “What do you have against me, that you have come to me to fight against my land?” 
#versenum(13) And the king of the #myname[Ammonites] answered the messengers of #myhl(menColor)[Jephthah], “Because #myname[Israel] on coming up from #myhl(placesColor)[Egypt] took away my land, from the #myhl(placesColor)[Arnon] to the #myhl(placesColor)[Jabbok] and to the #myhl(placesColor)[Jordan]; now therefore restore it #underline[peaceably].” 
#versenum(14) #myhl(menColor)[Jephthah] again sent messengers to the king of the #myname[Ammonites] 
#versenum(15) and said to him, “Thus says #myhl(menColor)[Jephthah]: #myname[Israel] did not take away the land of #myhl(placesColor)[Moab] or the land of the #myname[Ammonites], 
#versenum(16) but when they came up from #myhl(placesColor)[Egypt], #myname[Israel] went through the wilderness to the #myhl(placesColor)[Red Sea] and came to #myhl(placesColor)[Kadesh]. 
#versenum(17) #myname[Israel] then sent messengers to the king of #myhl(placesColor)[Edom], saying, ‘Please let us pass through your land,’ but the king of #myhl(placesColor)[Edom] would not listen. And they sent also to the king of #myhl(placesColor)[Moab], but he would not #underline[consent]. So #myname[Israel] remained at #myhl(placesColor)[Kadesh].


  
#versenum(18) “Then they journeyed through the wilderness and went around the land of #myhl(placesColor)[Edom] and the land of #myhl(placesColor)[Moab] and arrived on the east side of the land of #myhl(placesColor)[Moab] and #underline[camped] on the other side of the #myhl(placesColor)[Arnon]. But they did not enter the territory of #myhl(placesColor)[Moab], for the #myhl(placesColor)[Arnon] was the boundary of #myhl(placesColor)[Moab]. 
#versenum(19) #myname[Israel] then sent messengers to #myhl(menColor)[Sihon] king of the #myname[Amorites], king of #myhl(placesColor)[Heshbon], and #myname[Israel] said to him, ‘Please let us pass through your land to our country,’ 
#versenum(20) but #myhl(menColor)[Sihon] did not #underline[trust] #myname[Israel] to pass through his territory, so #myhl(menColor)[Sihon] gathered all his people together and encamped at #myhl(placesColor)[Jahaz] and fought with #myname[Israel]. 
#versenum(21) And the #myhl(divineColor)[#smallcaps[Lord], the God of Israel], gave #myhl(menColor)[Sihon] and all his people into the hand of #myname[Israel], and they defeated them. So #myname[Israel] took possession of all the land of the #myname[Amorites], who inhabited that country. 
#versenum(22) And they took possession of all the territory of the #myname[Amorites] from the #myhl(placesColor)[Arnon] to the #myhl(placesColor)[Jabbok] and from the wilderness to the #myhl(placesColor)[Jordan]. 
#versenum(23) So then the #myhl(divineColor)[#smallcaps[Lord], the God of Israel], dispossessed the #myname[Amorites] from before his people #myname[Israel]; and are you to take possession of them? 
#versenum(24) Will you not possess what #underline[#myname[Chemosh]] your god gives you to possess? And all that the #myhl(divineColor)[#smallcaps[Lord]] our #myhl(divineColor)[God] has dispossessed before us, we will possess. 
#versenum(25) Now are you any better than #myhl(menColor)[Balak] the son of #myhl(menColor)[Zippor], king of #myhl(placesColor)[Moab]? Did he ever contend against #myname[Israel], or did he ever go to war with them? 
#versenum(26) While #myname[Israel] lived in #myhl(placesColor)[Heshbon] and its villages, and in #myhl(placesColor)[Aroer] and its villages, and in all the cities that are on the banks of the #myhl(placesColor)[Arnon], #mynumber[300] years, why did you not deliver them within that time? 
#versenum(27) I therefore have not sinned against you, and you do me #underline[wrong] by making war on me. The #myhl(divineColor)[#smallcaps[Lord]], the Judge, #underline[decide] this day between the people of #myname[Israel] and the people of #underline[#myname[Ammon]].” 
#versenum(28) But the king of the #myname[Ammonites] did not listen to the words of #myhl(menColor)[Jephthah] that he sent to him.


  
#section-heading[Jephthah’s Tragic Vow]


#versenum(29) Then the #myhl(divineColor)[Spirit] of the #myhl(divineColor)[#smallcaps[Lord]] was upon #myhl(menColor)[Jephthah], and he passed through #myhl(placesColor)[Gilead] and #myhl(placesColor)[Manasseh] and passed on to #myhl(placesColor)[Mizpah] of #myhl(placesColor)[Gilead], and from #myhl(placesColor)[Mizpah] of #myhl(placesColor)[Gilead] he passed on to the #myname[Ammonites]. 
#versenum(30) And #myhl(menColor)[Jephthah] made a vow to the #myhl(divineColor)[#smallcaps[Lord]] and said, “If you will give the #myname[Ammonites] into my hand, 
#versenum(31) then whatever#footnote[Judges 11:31 Or #emph[whoever]] comes out from the doors of my house to meet me when I return in peace from the #myname[Ammonites] shall be the #myhl(divineColor)[#smallcaps[Lord]]’s, and I will offer it#footnote[Judges 11:31 Or #emph[him]] up for a burnt offering.” 
#versenum(32) So #myhl(menColor)[Jephthah] crossed over to the #myname[Ammonites] to fight against them, and the #myhl(divineColor)[#smallcaps[Lord]] gave them into his hand. 
#versenum(33) And he struck them from #myhl(placesColor)[Aroer] to the neighborhood of #underline[#myhl(placesColor)[Minnith]], #mynumber[twenty] cities, and as far as #underline[#myhl(placesColor)[Abel-keramim]], with a great blow. So the #myname[Ammonites] were subdued before the people of #myname[Israel].


  
#versenum(34) Then #myhl(menColor)[Jephthah] came to his home at #myhl(placesColor)[Mizpah]. And behold, his daughter came out to meet him with #underline[tambourines] and with dances. She was his only child; besides her he had neither son nor daughter. 
#versenum(35) And as soon as he saw her, he tore his clothes and said, “Alas, my daughter! You have brought me very low, and you have become the cause of great trouble to me. For I have opened my mouth to the #myhl(divineColor)[#smallcaps[Lord]], and I cannot take back my vow.” 
#versenum(36) And she said to him, “My father, you have opened your mouth to the #myhl(divineColor)[#smallcaps[Lord]]; do to me according to what has gone out of your mouth, now that the #myhl(divineColor)[#smallcaps[Lord]] has avenged you on your enemies, on the #myname[Ammonites].” 
#versenum(37) So she said to her father, “Let this thing be done for me: leave me alone #mynumber[two] months, that I may go up and down on the mountains and #underline[weep] for my virginity, I and my companions.” 
#versenum(38) So he said, “Go.” Then he sent her away for #mynumber[two] months, and she departed, she and her companions, and wept for her virginity on the mountains. 
#versenum(39) And at the end of #mynumber[two] months, she returned to her father, who did with her according to his vow that he had made. She had never known a man, and it became a custom in #myhl(placesColor)[Israel] 
#versenum(40) that the daughters of #myname[Israel] went year by year to #underline[lament] the daughter of #myhl(menColor)[Jephthah] the #myname[Gileadite] #mynumber[four] days in the year.


  
#chapter-heading[Judges 12]


#section-heading[Jephthah’s Conflict with Ephraim]


#versenum(1) The men of #myname[Ephraim] were called to arms, and they crossed to #myhl(placesColor)[Zaphon] and said to #myhl(menColor)[Jephthah], “Why did you #underline[cross] over to fight against the #myname[Ammonites] and did not call us to go with you? We will burn your house over you with fire.” 
#versenum(2) And #myhl(menColor)[Jephthah] said to them, “I and my people had a great #underline[dispute] with the #myname[Ammonites], and when I called you, you did not save me from their hand. 
#versenum(3) And when I saw that you would not save me, I took my life in my hand and crossed over against the #myname[Ammonites], and the #myhl(divineColor)[#smallcaps[Lord]] gave them into my hand. Why then have you come up to me this day to fight against me?” 
#versenum(4) Then #myhl(menColor)[Jephthah] gathered all the men of #myhl(placesColor)[Gilead] and fought with #myname[Ephraim]. And the men of #myhl(placesColor)[Gilead] struck #myname[Ephraim], because they said, “You are fugitives of #myname[Ephraim], you #myname[Gileadites], in the midst of #myname[Ephraim] and #myname[Manasseh].” 
#versenum(5) And the #myname[Gileadites] captured the fords of the #myhl(placesColor)[Jordan] against the #myname[Ephraimites]. And when any of the fugitives of #myname[Ephraim] said, “Let me go over,” the men of #myhl(placesColor)[Gilead] said to him, “Are you an #underline[#myname[Ephraimite]]?” When he said, “No,” 
#versenum(6) they said to him, “Then say #underline[Shibboleth],” and he said, “#underline[Sibboleth],” for he could not #underline[pronounce] it right. Then they seized him and #underline[slaughtered] him at the fords of the #myhl(placesColor)[Jordan]. At that time #underline[#mynumber[42,000]] of the #myname[Ephraimites] fell.


  
#versenum(7) #myhl(menColor)[Jephthah] judged #myname[Israel] #mynumber[six] years. Then #myhl(menColor)[Jephthah] the #myname[Gileadite] died and was buried in his city in #myhl(placesColor)[Gilead].#footnote[Judges 12:7 Septuagint; Hebrew #emph[in the cities of Gilead]]


  
#section-heading[Ibzan, Elon, and Abdon]


#versenum(8) After him #myhl(menColor)[Ibzan] of #myhl(placesColor)[Bethlehem] judged #myname[Israel]. 
#versenum(9) He had #mynumber[thirty] sons, and #mynumber[thirty] daughters he gave in marriage outside his clan, and #mynumber[thirty] daughters he brought in from outside for his sons. And he judged #myname[Israel] #mynumber[seven] years. 
#versenum(10) Then #myhl(menColor)[Ibzan] died and was buried at #myhl(placesColor)[Bethlehem].


  
#versenum(11) After him #myhl(menColor)[Elon] the #myname[Zebulunite] judged #myname[Israel], and he judged #myname[Israel] #mynumber[ten] years. 
#versenum(12) Then #myhl(menColor)[Elon] the #myname[Zebulunite] died and was buried at #myhl(placesColor)[Aijalon] in the land of #myname[Zebulun].


  
#versenum(13) After him #myhl(menColor)[Abdon] the son of #myhl(menColor)[Hillel] the #myname[Pirathonite] judged #myname[Israel]. 
#versenum(14) He had #mynumber[forty] sons and #mynumber[thirty] #underline[grandsons], who rode on #mynumber[seventy] donkeys, and he judged #myname[Israel] #mynumber[eight] years. 
#versenum(15) Then #myhl(menColor)[Abdon] the son of #myhl(menColor)[Hillel] the #myname[Pirathonite] died and was buried at #underline[#myhl(placesColor)[Pirathon]] in the land of #myname[Ephraim], in the hill country of the #myname[Amalekites].


  
#chapter-heading[Judges 13]


#section-heading[The Birth of Samson]


#versenum(1) And the people of #myname[Israel] again did what was evil in the sight of the #myhl(divineColor)[#smallcaps[Lord]], so the #myhl(divineColor)[#smallcaps[Lord]] gave them into the hand of the #myname[Philistines] for #mynumber[forty] years.


  
#versenum(2) There was a certain man of #myhl(placesColor)[Zorah], of the tribe of the #myname[Danites], whose name was #myhl(menColor)[Manoah]. And his wife was barren and had no children. 
#versenum(3) And the #myhl(divineColor)[angel of the #smallcaps[Lord]] appeared to the woman and said to her, “Behold, you are barren and have not #underline[borne] children, but you shall conceive and bear a son. 
#versenum(4) Therefore be careful and drink no wine or strong drink, and eat nothing unclean, 
#versenum(5) for behold, you shall conceive and bear a son. No razor shall come upon his head, for the child shall be a #myname[Nazirite] to #myhl(divineColor)[God] from the womb, and he shall begin to save #myname[Israel] from the hand of the #myname[Philistines].” 
#versenum(6) Then the woman came and told her husband, “A man of #myhl(divineColor)[God] came to me, and his appearance was like the appearance of the angel of #myhl(divineColor)[God], very #underline[awesome]. I did not ask him where he was from, and he did not tell me his name, 
#versenum(7) but he said to me, ‘Behold, you shall conceive and bear a son. So then drink no wine or strong drink, and eat nothing unclean, for the child shall be a #myname[Nazirite] to #myhl(divineColor)[God] from the womb to the day of his death.’”


  
#versenum(8) Then #myhl(menColor)[Manoah] #underline[prayed] to the #myhl(divineColor)[#smallcaps[Lord]] and said, “O #myhl(divineColor)[Lord], please let the man of #myhl(divineColor)[God] whom you sent come again to us and teach us what we are to do with the child who will be born.” 
#versenum(9) And #myhl(divineColor)[God] #underline[listened] to the voice of #myhl(menColor)[Manoah], and the angel of #myhl(divineColor)[God] came again to the woman as she sat in the field. But #myhl(menColor)[Manoah] her husband was not with her. 
#versenum(10) So the woman ran quickly and told her husband, “Behold, the man who came to me the other day has appeared to me.” 
#versenum(11) And #myhl(menColor)[Manoah] arose and went after his wife and came to the man and said to him, “Are you the man who spoke to this woman?” And he said, “I am.” 
#versenum(12) And #myhl(menColor)[Manoah] said, “Now when your words come true, what is to be the #underline[child’s] manner of life, and what is his #underline[mission]?” 
#versenum(13) And the #myhl(divineColor)[angel of the #smallcaps[Lord]] said to #myhl(menColor)[Manoah], “Of all that I said to the woman let her be careful. 
#versenum(14) She may not eat of anything that comes from the vine, neither let her drink wine or strong drink, or eat any unclean thing. All that I commanded her let her observe.”


  
#versenum(15) #myhl(menColor)[Manoah] said to the #myhl(divineColor)[angel of the #smallcaps[Lord]], “Please let us detain you and prepare a young goat for you.” 
#versenum(16) And the #myhl(divineColor)[angel of the #smallcaps[Lord]] said to #myhl(menColor)[Manoah], “If you detain me, I will not eat of your food. But if you prepare a burnt offering, then offer it to the #myhl(divineColor)[#smallcaps[Lord]].” (For #myhl(menColor)[Manoah] did not know that he was the #myhl(divineColor)[angel of the #smallcaps[Lord]].) 
#versenum(17) And #myhl(menColor)[Manoah] said to the #myhl(divineColor)[angel of the #smallcaps[Lord]], “What is your name, so that, when your words come true, we may #underline[honor] you?” 
#versenum(18) And the #myhl(divineColor)[angel of the #smallcaps[Lord]] said to him, “Why do you ask my name, #underline[seeing] it is wonderful?” 
#versenum(19) So #myhl(menColor)[Manoah] took the young goat with the grain offering, and offered it on the rock to the #myhl(divineColor)[#smallcaps[Lord]], to the one who #underline[works]#footnote[Judges 13:19 Septuagint, Vulgate; Hebrew #emph[Lord, and working]] wonders, and #myhl(menColor)[Manoah] and his wife were watching. 
#versenum(20) And when the flame went up toward heaven from the altar, the #myhl(divineColor)[angel of the #smallcaps[Lord]] went up in the flame of the altar. Now #myhl(menColor)[Manoah] and his wife were watching, and they fell on their faces to the ground.


  
#versenum(21) The #myhl(divineColor)[angel of the #smallcaps[Lord]] appeared no more to #myhl(menColor)[Manoah] and to his wife. Then #myhl(menColor)[Manoah] knew that he was the #myhl(divineColor)[angel of the #smallcaps[Lord]]. 
#versenum(22) And #myhl(menColor)[Manoah] said to his wife, “We shall surely die, for we have seen #myhl(divineColor)[God].” 
#versenum(23) But his wife said to him, “If the #myhl(divineColor)[#smallcaps[Lord]] had meant to kill us, he would not have #underline[accepted] a burnt offering and a grain offering at our hands, or #underline[shown] us all these things, or now #underline[announced] to us such things as these.” 
#versenum(24) And the woman bore a son and called his name #myhl(menColor)[Samson]. And the young man grew, and the #myhl(divineColor)[#smallcaps[Lord]] blessed him. 
#versenum(25) And the #myhl(divineColor)[Spirit] of the #myhl(divineColor)[#smallcaps[Lord]] began to #underline[stir] him in #myhl(placesColor)[Mahaneh-dan], between #myhl(placesColor)[Zorah] and #myhl(placesColor)[Eshtaol].


  
#chapter-heading[Judges 14]


#section-heading[Samson’s Marriage]


#versenum(1) #myhl(menColor)[Samson] went down to #myhl(placesColor)[Timnah], and at #myhl(placesColor)[Timnah] he saw one of the daughters of the #myname[Philistines]. 
#versenum(2) Then he came up and told his father and mother, “I saw one of the daughters of the #myname[Philistines] at #myhl(placesColor)[Timnah]. Now get her for me as my wife.” 
#versenum(3) But his father and mother said to him, “Is there not a woman among the daughters of your relatives, or among all our people, that you must go to take a wife from the uncircumcised #myname[Philistines]?” But #myhl(menColor)[Samson] said to his father, “Get her for me, for she is right in my eyes.”


  
#versenum(4) His father and mother did not know that it was from the #myhl(divineColor)[#smallcaps[Lord]], for he was seeking an #underline[opportunity] against the #myname[Philistines]. At that time the #myname[Philistines] ruled over #myname[Israel].


  
#versenum(5) Then #myhl(menColor)[Samson] went down with his father and mother to #myhl(placesColor)[Timnah], and they came to the vineyards of #myhl(placesColor)[Timnah]. And behold, a young lion came toward him #underline[roaring]. 
#versenum(6) Then the #myhl(divineColor)[Spirit] of the #myhl(divineColor)[#smallcaps[Lord]] rushed upon him, and although he had nothing in his hand, he tore the lion in pieces as one #underline[tears] a young goat. But he did not tell his father or his mother what he had done. 
#versenum(7) Then he went down and #underline[talked] with the woman, and she was right in #myhl(menColor)[Samson]’s eyes.


  
#versenum(8) After some days he returned to take her. And he turned aside to see the carcass of the lion, and behold, there was a #underline[swarm] of #underline[bees] in the body of the lion, and honey. 
#versenum(9) He scraped it out into his hands and went on, eating as he went. And he came to his father and mother and gave some to them, and they ate. But he did not tell them that he had scraped the honey from the carcass of the lion.


  
#versenum(10) His father went down to the woman, and #myhl(menColor)[Samson] prepared a feast there, for so the young men used to do. 
#versenum(11) As soon as the people saw him, they brought #mynumber[thirty] companions to be with him. 
#versenum(12) And #myhl(menColor)[Samson] said to them, “Let me now put a riddle to you. If you can tell me what it is, within the #mynumber[seven] days of the feast, and find it out, then I will give you #mynumber[thirty] linen garments and #mynumber[thirty] changes of clothes, 
#versenum(13) but if you cannot tell me what it is, then you shall give me #mynumber[thirty] linen garments and #mynumber[thirty] changes of clothes.” And they said to him, “Put your riddle, that we may hear it.” 
#versenum(14) And he said to them,


    “Out of the #underline[eater] came something to eat.\
    Out of the strong came something #underline[sweet].”\


      And in #mynumber[three] days they could not #underline[solve] the riddle.


  
#versenum(15) On the #mynumber[fourth]#footnote[Judges 14:15 Septuagint, Syriac; Hebrew #emph[seventh]] day they said to #myhl(menColor)[Samson]’s wife, “#underline[Entice] your husband to tell us what the riddle is, lest we burn you and your father’s house with fire. Have you invited us here to #underline[impoverish] us?” 
#versenum(16) And #myhl(menColor)[Samson]’s wife wept over him and said, “You only hate me; you do not love me. You have put a riddle to my people, and you have not told me what it is.” And he said to her, “Behold, I have not told my father nor my mother, and shall I tell you?” 
#versenum(17) She wept before him the #mynumber[seven] days that their feast #underline[lasted], and on the #mynumber[seventh] day he told her, because she pressed him hard. Then she told the riddle to her people. 
#versenum(18) And the men of the city said to him on the #mynumber[seventh] day before the sun went down,


    “What is #underline[sweeter] than honey?\
    What is #underline[stronger] than a lion?”\


      And he said to them,


    “If you had not #underline[plowed] with my #underline[heifer],\
    you would not have found out my riddle.”\


      
#versenum(19) And the #myhl(divineColor)[Spirit] of the #myhl(divineColor)[#smallcaps[Lord]] rushed upon him, and he went down to #myhl(placesColor)[Ashkelon] and struck down #mynumber[thirty] men of the town and took their spoil and gave the garments to those who had told the riddle. In #underline[hot] anger he went back to his father’s house. 
#versenum(20) And #myhl(menColor)[Samson]’s wife was given to his companion, who had been his #underline[best] man.


  
#chapter-heading[Judges 15]


#section-heading[Samson Defeats the Philistines]


#versenum(1) After some days, at the time of wheat harvest, #myhl(menColor)[Samson] went to #underline[visit] his wife with a young goat. And he said, “I will go in to my wife in the chamber.” But her father would not allow him to go in. 
#versenum(2) And her father said, “I #underline[really] thought that you utterly #underline[hated] her, so I gave her to your companion. Is not her younger #underline[sister] more beautiful than she? Please take her #underline[instead].” 
#versenum(3) And #myhl(menColor)[Samson] said to them, “This time I shall be #underline[innocent] in regard to the #myname[Philistines], when I do them harm.” 
#versenum(4) So #myhl(menColor)[Samson] went and caught #mynumber[300] foxes and took torches. And he turned them tail to tail and put a #underline[torch] between each #underline[pair] of #underline[tails]. 
#versenum(5) And when he had set fire to the torches, he let the foxes go into the standing grain of the #myname[Philistines] and set fire to the #underline[stacked] grain and the standing grain, as well as the olive orchards. 
#versenum(6) Then the #myname[Philistines] said, “Who has done this?” And they said, “#myhl(menColor)[Samson], the son-in-law of the #underline[#myname[Timnite]], because he has taken his wife and given her to his companion.” And the #myname[Philistines] came up and burned her and her father with fire. 
#versenum(7) And #myhl(menColor)[Samson] said to them, “If this is what you do, I swear I will be avenged on you, and after that I will #underline[quit].” 
#versenum(8) And he struck them #underline[hip] and thigh with a great blow, and he went down and stayed in the cleft of the #myhl(placesColor)[rock of Etam].


  
#versenum(9) Then the #myname[Philistines] came up and encamped in #myhl(placesColor)[Judah] and made a #underline[raid] on #myhl(placesColor)[Lehi]. 
#versenum(10) And the men of #myname[Judah] said, “Why have you come up against us?” They said, “We have come up to bind #myhl(menColor)[Samson], to do to him as he did to us.” 
#versenum(11) Then #mynumber[3,000] men of #myname[Judah] went down to the cleft of the #myhl(placesColor)[rock of Etam], and said to #myhl(menColor)[Samson], “Do you not know that the #myname[Philistines] are rulers over us? What then is this that you have done to us?” And he said to them, “As they did to me, so have I done to them.” 
#versenum(12) And they said to him, “We have come down to bind you, that we may give you into the hands of the #myname[Philistines].” And #myhl(menColor)[Samson] said to them, “Swear to me that you will not attack me yourselves.” 
#versenum(13) They said to him, “No; we will only bind you and give you into their hands. We will surely not kill you.” So they bound him with #mynumber[two] new ropes and brought him up from the rock.


  
#versenum(14) When he came to #myhl(placesColor)[Lehi], the #myname[Philistines] came #underline[shouting] to meet him. Then the #myhl(divineColor)[Spirit] of the #myhl(divineColor)[#smallcaps[Lord]] rushed upon him, and the ropes that were on his arms became as flax that has caught fire, and his #underline[bonds] melted off his hands. 
#versenum(15) And he found a fresh jawbone of a donkey, and put out his hand and took it, and with it he struck #mynumber[1,000] men. 
#versenum(16) And #myhl(menColor)[Samson] said,


    “With the jawbone of a donkey,\
    #vin heaps upon heaps,\
    with the jawbone of a donkey\
    #vin have I struck down a #mynumber[thousand] men.”\


      
#versenum(17) As soon as he had finished #underline[speaking], he threw away the jawbone out of his hand. And that place was called #underline[#myhl(placesColor)[Ramath-lehi]].#footnote[Judges 15:17 #emph[Ramath-lehi] means #emph[the hill of the jawbone]]


  
#versenum(18) And he was very thirsty, and he called upon the #myhl(divineColor)[#smallcaps[Lord]] and said, “You have #underline[granted] this great #underline[salvation] by the hand of your servant, and shall I now die of #underline[thirst] and fall into the hands of the uncircumcised?” 
#versenum(19) And #myhl(divineColor)[God] #underline[split] open the #underline[hollow] place that is at #myhl(placesColor)[Lehi], and water came out from it. And when he drank, his spirit returned, and he #underline[revived]. Therefore the name of it was called #underline[#myhl(placesColor)[En-hakkore]];#footnote[Judges 15:19 #emph[En-hakkore] means #emph[the spring of him who called]] it is at #myhl(placesColor)[Lehi] to this day. 
#versenum(20) And he judged #myname[Israel] in the days of the #myname[Philistines] #mynumber[twenty] years.


  
#chapter-heading[Judges 16]


#section-heading[Samson and Delilah]


#versenum(1) #myhl(menColor)[Samson] went to #myhl(placesColor)[Gaza], and there he saw a prostitute, and he went in to her. 
#versenum(2) The #underline[#myname[Gazites]] were told, “#myhl(menColor)[Samson] has come here.” And they surrounded the place and set an ambush for him all night at the gate of the city. They kept quiet all night, saying, “Let us wait till the light of the morning; then we will kill him.” 
#versenum(3) But #myhl(menColor)[Samson] lay till midnight, and at midnight he arose and took hold of the doors of the gate of the city and the #mynumber[two] #underline[posts], and pulled them up, bar and all, and put them on his #underline[shoulders] and carried them to the top of the hill that is in front of #myhl(placesColor)[Hebron].


  
#versenum(4) After this he #underline[loved] a woman in the #myhl(placesColor)[Valley of #underline[Sorek]], whose name was #myhl(womenColor)[Delilah]. 
#versenum(5) And the lords of the #myname[Philistines] came up to her and said to her, “#underline[Seduce] him, and see where his great strength lies, and by what #underline[means] we may #underline[overpower] him, that we may bind him to #underline[humble] him. And we will each give you #mynumber[1,100] pieces of silver.” 
#versenum(6) So #myhl(womenColor)[Delilah] said to #myhl(menColor)[Samson], “Please tell me where your great strength lies, and how you might be bound, that one could #underline[subdue] you.”


  
#versenum(7) #myhl(menColor)[Samson] said to her, “If they bind me with #mynumber[seven] fresh bowstrings that have not been dried, then I shall become weak and be like any other man.” 
#versenum(8) Then the lords of the #myname[Philistines] brought up to her #mynumber[seven] fresh bowstrings that had not been dried, and she bound him with them. 
#versenum(9) Now she had men lying in ambush in an inner chamber. And she said to him, “The #myname[Philistines] are upon you, #myhl(menColor)[Samson]!” But he snapped the bowstrings, as a thread of flax #underline[snaps] when it touches the fire. So the secret of his strength was not known.


  
#versenum(10) Then #myhl(womenColor)[Delilah] said to #myhl(menColor)[Samson], “Behold, you have mocked me and told me lies. Please tell me how you might be bound.” 
#versenum(11) And he said to her, “If they bind me with new ropes that have not been used, then I shall become weak and be like any other man.” 
#versenum(12) So #myhl(womenColor)[Delilah] took new ropes and bound him with them and said to him, “The #myname[Philistines] are upon you, #myhl(menColor)[Samson]!” And the men lying in ambush were in an inner chamber. But he snapped the ropes off his arms like a thread.


  
#versenum(13) Then #myhl(womenColor)[Delilah] said to #myhl(menColor)[Samson], “Until now you have mocked me and told me lies. Tell me how you might be bound.” And he said to her, “If you #underline[weave] the #mynumber[seven] locks of my head with the web and #underline[fasten] it tight with the pin, then I shall become weak and be like any other man.” 
#versenum(14) So while he #underline[slept], #myhl(womenColor)[Delilah] took the #mynumber[seven] locks of his head and #underline[wove] them into the web.#footnote[Judges 16:14 Compare Septuagint; Hebrew lacks #emph[and fasten it tight . . . into the web]] And she made them tight with the pin and said to him, “The #myname[Philistines] are upon you, #myhl(menColor)[Samson]!” But he awoke from his sleep and pulled away the pin, the #underline[loom], and the web.


  
#versenum(15) And she said to him, “How can you say, ‘I love you,’ when your heart is not with me? You have mocked me these #mynumber[three] times, and you have not told me where your great strength lies.” 
#versenum(16) And when she pressed him hard with her words day after day, and urged him, his soul was #underline[vexed] to death. 
#versenum(17) And he told her all his heart, and said to her, “A razor has never come upon my head, for I have been a #myname[Nazirite] to #myhl(divineColor)[God] from my mother’s womb. If my head is shaved, then my strength will leave me, and I shall become weak and be like any other man.”


  
#versenum(18) When #myhl(womenColor)[Delilah] saw that he had told her all his heart, she sent and called the lords of the #myname[Philistines], saying, “Come up again, for he has told me all his heart.” Then the lords of the #myname[Philistines] came up to her and brought the money in their hands. 
#versenum(19) She made him sleep on her #underline[knees]. And she called a man and had him #underline[shave] off the #mynumber[seven] locks of his head. Then she began to #underline[torment] him, and his strength left him. 
#versenum(20) And she said, “The #myname[Philistines] are upon you, #myhl(menColor)[Samson]!” And he awoke from his sleep and said, “I will go out as at other times and #underline[shake] myself #underline[free].” But he did not know that the #myhl(divineColor)[#smallcaps[Lord]] had left him. 
#versenum(21) And the #myname[Philistines] seized him and #underline[gouged] out his eyes and brought him down to #myhl(placesColor)[Gaza] and bound him with bronze #underline[shackles]. And he ground at the #underline[mill] in the prison. 
#versenum(22) But the hair of his head began to #underline[grow] again after it had been shaved.


  
#section-heading[The Death of Samson]


#versenum(23) Now the lords of the #myname[Philistines] gathered to offer a great sacrifice to #underline[#myname[Dagon]] their god and to rejoice, and they said, “Our god has given #myhl(menColor)[Samson] our enemy into our hand.” 
#versenum(24) And when the people saw him, they #underline[praised] their god. For they said, “Our god has given our enemy into our hand, the #underline[ravager] of our country, who has killed many of us.”#footnote[Judges 16:24 Or #emph[who has multiplied our slain]] 
#versenum(25) And when their hearts were merry, they said, “Call #myhl(menColor)[Samson], that he may #underline[entertain] us.” So they called #myhl(menColor)[Samson] out of the prison, and he entertained them. They made him stand between the pillars. 
#versenum(26) And #myhl(menColor)[Samson] said to the young man who held him by the hand, “Let me #underline[feel] the pillars on which the house #underline[rests], that I may #underline[lean] against them.” 
#versenum(27) Now the house was full of men and women. All the lords of the #myname[Philistines] were there, and on the roof there were about #mynumber[3,000] men and women, who looked on while #myhl(menColor)[Samson] entertained.


  
#versenum(28) Then #myhl(menColor)[Samson] called to the #myhl(divineColor)[#smallcaps[Lord]] and said, “O #myhl(divineColor)[Lord] #myhl(divineColor)[GOD], please remember me and please strengthen me only this once, O #myhl(divineColor)[God], that I may be avenged on the #myname[Philistines] for my #mynumber[two] eyes.” 
#versenum(29) And #myhl(menColor)[Samson] #underline[grasped] the #mynumber[two] middle pillars on which the house rested, and he #underline[leaned] his weight against them, his right hand on the one and his left hand on the other. 
#versenum(30) And #myhl(menColor)[Samson] said, “Let me die with the #myname[Philistines].” Then he bowed with all his strength, and the house fell upon the lords and upon all the people who were in it. So the dead whom he killed at his death were more than those whom he had killed #underline[during] his life. 
#versenum(31) Then his brothers and all his family came down and took him and brought him up and buried him between #myhl(placesColor)[Zorah] and #myhl(placesColor)[Eshtaol] in the tomb of #myhl(menColor)[Manoah] his father. He had judged #myname[Israel] #mynumber[twenty] years.


  
#chapter-heading[Judges 17]


#section-heading[Micah and the Levite]


#versenum(1) There was a man of #myhl(placesColor)[the hill country of Ephraim], whose name was #myhl(menColor)[Micah]. 
#versenum(2) And he said to his mother, “The #mynumber[1,100] pieces of silver that were taken from you, about which you #underline[uttered] a curse, and also spoke it in my ears, behold, the silver is with me; I took it.” And his mother said, “Blessed be my son by the #myhl(divineColor)[#smallcaps[Lord]].” 
#versenum(3) And he restored the #mynumber[1,100] pieces of silver to his mother. And his mother said, “I #underline[dedicate] the silver to the #myhl(divineColor)[#smallcaps[Lord]] from my hand for my son, to make a carved image and a metal image. Now therefore I will restore it to you.” 
#versenum(4) So when he restored the money to his mother, his mother took #mynumber[200] pieces of silver and gave it to the #underline[silversmith], who made it into a carved image and a metal image. And it was in the house of #myhl(menColor)[Micah]. 
#versenum(5) And the man #myhl(menColor)[Micah] had a #underline[shrine], and he made an ephod and household gods, and ordained#footnote[Judges 17:5 Hebrew #emph[filled the hand of]; also verse 12] one of his sons, who became his priest. 
#versenum(6) In those days there was no king in #myhl(placesColor)[Israel]. Everyone did what was right in his own eyes.


  
#versenum(7) Now there was a young man of #myhl(placesColor)[Bethlehem in Judah], of the family of #myname[Judah], who was a #myname[Levite], and he #underline[sojourned] there. 
#versenum(8) And the man departed from the town of #myhl(placesColor)[Bethlehem in Judah] to sojourn where he could find a place. And as he journeyed, he came to #myhl(placesColor)[the hill country of Ephraim] to the house of #myhl(menColor)[Micah]. 
#versenum(9) And #myhl(menColor)[Micah] said to him, “Where do you come from?” And he said to him, “I am a #myname[Levite] of #myhl(placesColor)[Bethlehem in Judah], and I am going to sojourn where I may find a place.” 
#versenum(10) And #myhl(menColor)[Micah] said to him, “Stay with me, and be to me a father and a priest, and I will give you #mynumber[ten] pieces of silver a year and a #underline[suit] of clothes and your living.” And the #myname[Levite] went in. 
#versenum(11) And the #myname[Levite] was content to dwell with the man, and the young man became to him like one of his sons. 
#versenum(12) And #myhl(menColor)[Micah] ordained the #myname[Levite], and the young man became his priest, and was in the house of #myhl(menColor)[Micah]. 
#versenum(13) Then #myhl(menColor)[Micah] said, “Now I know that the #myhl(divineColor)[#smallcaps[Lord]] will #underline[prosper] me, because I have a #myname[Levite] as priest.”


  
#chapter-heading[Judges 18]


#section-heading[Danites Take the Levite and the Idol]


#versenum(1) In those days there was no king in #myhl(placesColor)[Israel]. And in those days the tribe of the people of #myname[Dan] was seeking for itself an inheritance to dwell in, for until then no inheritance among the tribes of #myname[Israel] had fallen to them. 
#versenum(2) So the people of #myname[Dan] sent #mynumber[five] able men from the whole number of their tribe, from #myhl(placesColor)[Zorah] and from #myhl(placesColor)[Eshtaol], to spy out the land and to explore it. And they said to them, “Go and explore the land.” And they came to #myhl(placesColor)[the hill country of Ephraim], to the house of #myhl(menColor)[Micah], and lodged there. 
#versenum(3) When they were by the house of #myhl(menColor)[Micah], they #underline[recognized] the voice of the young #myname[Levite]. And they turned aside and said to him, “Who brought you here? What are you doing in this place? What is your business here?” 
#versenum(4) And he said to them, “This is how #myhl(menColor)[Micah] dealt with me: he has hired me, and I have become his priest.” 
#versenum(5) And they said to him, “#underline[Inquire] of #myhl(divineColor)[God], please, that we may know whether the journey on which we are #underline[setting] out will #underline[succeed].” 
#versenum(6) And the priest said to them, “Go in peace. The journey on which you go is under the #underline[eye] of the #myhl(divineColor)[#smallcaps[Lord]].”


  
#versenum(7) Then the #mynumber[five] men departed and came to #myhl(placesColor)[Laish] and saw the people who were there, how they lived in #underline[security], after the manner of the #myname[Sidonians], quiet and unsuspecting, lacking#footnote[Judges 18:7 Compare 18:10; the meaning of the Hebrew word is uncertain] nothing that is in the earth and #underline[possessing] wealth, and how they were far from the #myname[Sidonians] and had no dealings with anyone. 
#versenum(8) And when they came to their brothers at #myhl(placesColor)[Zorah] and #myhl(placesColor)[Eshtaol], their brothers said to them, “What do you report?” 
#versenum(9) They said, “Arise, and let us go up against them, for we have seen the land, and behold, it is very good. And will you do nothing? Do not be #underline[slow] to go, to enter in and possess the land. 
#versenum(10) As soon as you go, you will come to an unsuspecting people. The land is #underline[spacious], for #myhl(divineColor)[God] has given it into your hands, a place where there is no lack of anything that is in the earth.”


  
#versenum(11) So #mynumber[600] men of the tribe of #myname[Dan], armed with weapons of war, set out from #myhl(placesColor)[Zorah] and #myhl(placesColor)[Eshtaol], 
#versenum(12) and went up and encamped at #myhl(placesColor)[Kiriath-jearim] in #myhl(placesColor)[Judah]. On this account that place is called #myhl(placesColor)[Mahaneh-dan]#footnote[Judges 18:12 #emph[Mahaneh-dan] means #emph[camp of Dan]] to this day; behold, it is west of #myhl(placesColor)[Kiriath-jearim]. 
#versenum(13) And they passed on from there to #myhl(placesColor)[the hill country of Ephraim], and came to the house of #myhl(menColor)[Micah].


  
#versenum(14) Then the #mynumber[five] men who had gone to scout out the country of #myhl(placesColor)[Laish] said to their brothers, “Do you know that in these houses there are an ephod, household gods, a carved image, and a metal image? Now therefore consider what you will do.” 
#versenum(15) And they turned aside there and came to the house of the young #myname[Levite], at the home of #myhl(menColor)[Micah], and asked him about his #underline[welfare]. 
#versenum(16) Now the #mynumber[600] men of the #myname[Danites], armed with their weapons of war, stood by the entrance of the gate. 
#versenum(17) And the #mynumber[five] men who had gone to scout out the land went up and entered and took the carved image, the ephod, the household gods, and the metal image, while the priest stood by the entrance of the gate with the #mynumber[600] men armed with weapons of war. 
#versenum(18) And when these went into #myhl(menColor)[Micah]’s house and took the carved image, the ephod, the household gods, and the metal image, the priest said to them, “What are you doing?” 
#versenum(19) And they said to him, “Keep quiet; put your hand on your mouth and come with us and be to us a father and a priest. Is it better for you to be priest to the house of one man, or to be priest to a tribe and clan in #myhl(placesColor)[Israel]?” 
#versenum(20) And the #underline[priest’s] heart was #underline[glad]. He took the ephod and the household gods and the carved image and went along with the people.


  
#versenum(21) So they turned and departed, putting the little ones and the livestock and the #underline[goods] in front of them. 
#versenum(22) When they had gone a distance from the home of #myhl(menColor)[Micah], the men who were in the houses near #myhl(menColor)[Micah]’s house were called out, and they overtook the people of #myname[Dan]. 
#versenum(23) And they shouted to the people of #myname[Dan], who turned around and said to #myhl(menColor)[Micah], “What is the matter with you, that you come with such a company?” 
#versenum(24) And he said, “You take my gods that I made and the priest, and go away, and what have I left? How then do you ask me, ‘What is the matter with you?’” 
#versenum(25) And the people of #myname[Dan] said to him, “Do not let your voice be heard among us, lest angry fellows fall upon you, and you #underline[lose] your life with the lives of your household.” 
#versenum(26) Then the people of #myname[Dan] went their way. And when #myhl(menColor)[Micah] saw that they were too strong for him, he turned and went back to his home.


  
#versenum(27) But the people of #myname[Dan] took what #myhl(menColor)[Micah] had made, and the priest who belonged to him, and they came to #myhl(placesColor)[Laish], to a people quiet and unsuspecting, and struck them with the edge of the sword and burned the city with fire. 
#versenum(28) And there was no deliverer because it was far from #myhl(placesColor)[Sidon], and they had no dealings with anyone. It was in the valley that belongs to #underline[#myhl(placesColor)[Beth-rehob]]. Then they rebuilt the city and lived in it. 
#versenum(29) And they named the city #myname[Dan], after the name of #myname[Dan] their ancestor, who was born to #myname[Israel]; but the name of the city was #myhl(placesColor)[Laish] at the first. 
#versenum(30) And the people of #myname[Dan] set up the carved image for themselves, and #underline[#myhl(menColor)[Jonathan]] the son of #underline[#myhl(menColor)[Gershom]], son of #myhl(menColor)[Moses],#footnote[Judges 18:30 Or #emph[Manasseh]] and his sons were priests to the tribe of the #myname[Danites] until the day of the #underline[captivity] of the land. 
#versenum(31) So they set up #myhl(menColor)[Micah]’s carved image that he made, as long as the house of #myhl(divineColor)[God] was at #myhl(placesColor)[Shiloh].


  
#chapter-heading[Judges 19]


#section-heading[A Levite and His Concubine]


#versenum(1) In those days, when there was no king in #myhl(placesColor)[Israel], a certain #myname[Levite] was sojourning in the remote parts of #myhl(placesColor)[the hill country of Ephraim], who took to himself a concubine from #myhl(placesColor)[Bethlehem in Judah]. 
#versenum(2) And his concubine was #underline[unfaithful] to#footnote[Judges 19:2 Septuagint, Old Latin #emph[became angry with]] him, and she went away from him to her father’s house at #myhl(placesColor)[Bethlehem in Judah], and was there some #mynumber[four] months. 
#versenum(3) Then her husband arose and went after her, to speak kindly to her and bring her back. He had with him his servant and #mynumber[a couple] of donkeys. And she brought him into her father’s house. And when the girl’s father saw him, he came with #underline[joy] to meet him. 
#versenum(4) And his father-in-law, the girl’s father, made him stay, and he remained with him #mynumber[three] days. So they ate and drank and spent the night there. 
#versenum(5) And on the #mynumber[fourth] day they arose early in the morning, and he prepared to go, but the girl’s father said to his son-in-law, “Strengthen your heart with a morsel of bread, and after that you may go.” 
#versenum(6) So the #mynumber[two] of them sat and ate and drank together. And the girl’s father said to the man, “Be #underline[pleased] to spend the night, and let your heart be merry.” 
#versenum(7) And when the man rose up to go, his father-in-law pressed him, till he spent the night there again. 
#versenum(8) And on the #mynumber[fifth] day he arose early in the morning to depart. And the girl’s father said, “Strengthen your heart and wait until the day #underline[declines].” So they ate, both of them. 
#versenum(9) And when the man and his concubine and his servant rose up to depart, his father-in-law, the girl’s father, said to him, “Behold, now the day has #underline[waned] toward evening. Please, spend the night. Behold, the day #underline[draws] to its close. Lodge here and let your heart be merry, and tomorrow you shall arise early in the morning for your journey, and go home.”


  
#versenum(10) But the man would not spend the night. He rose up and departed and arrived opposite #myhl(placesColor)[Jebus] (that is, #myhl(placesColor)[Jerusalem]). He had with him #mynumber[a couple] of #underline[saddled] donkeys, and his concubine was with him. 
#versenum(11) When they were near #myhl(placesColor)[Jebus], the day was #underline[nearly] over, and the servant said to his master, “Come now, let us turn aside to this city of the #myname[Jebusites] and spend the night in it.” 
#versenum(12) And his master said to him, “We will not turn aside into the city of #underline[foreigners], who do not belong to the people of #myname[Israel], but we will pass on to #myhl(placesColor)[Gibeah].” 
#versenum(13) And he said to his young man, “Come and let us draw near to one of these places and spend the night at #myhl(placesColor)[Gibeah] or at #myhl(placesColor)[Ramah].” 
#versenum(14) So they passed on and went their way. And the sun went down on them near #myhl(placesColor)[Gibeah], which belongs to #myname[Benjamin], 
#versenum(15) and they turned aside there, to go in and spend the night at #myhl(placesColor)[Gibeah]. And he went in and sat down in the open square of the city, for no one took them into his house to spend the night.


  
#versenum(16) And behold, an old man was coming from his work in the field at evening. The man was from #myhl(placesColor)[the hill country of Ephraim], and he was sojourning in #myhl(placesColor)[Gibeah]. The men of the place were #myname[Benjaminites]. 
#versenum(17) And he lifted up his eyes and saw the #underline[traveler] in the open square of the city. And the old man said, “Where are you going? And where do you come from?” 
#versenum(18) And he said to him, “We are passing from #myhl(placesColor)[Bethlehem in Judah] to the remote parts of #myhl(placesColor)[the hill country of Ephraim], from which I come. I went to #myhl(placesColor)[Bethlehem in Judah], and I am going to the house of the #myhl(divineColor)[#smallcaps[Lord]],#footnote[Judges 19:18 Septuagint #emph[my home]; compare verse 29] but no one has taken me into his house. 
#versenum(19) We have #underline[straw] and feed for our donkeys, with bread and wine for me and your female servant and the young man with your servants. There is no lack of anything.” 
#versenum(20) And the old man said, “Peace be to you; I will care for all your #underline[wants]. Only, do not spend the night in the square.” 
#versenum(21) So he brought him into his house and gave the donkeys feed. And they #underline[washed] their feet, and ate and drank.


  
#section-heading[Gibeah’s Crime]


#versenum(22) As they were making their hearts merry, behold, the men of the city, worthless fellows, surrounded the house, beating on the door. And they said to the old man, the master of the house, “Bring out the man who came into your house, that we may know him.” 
#versenum(23) And the man, the master of the house, went out to them and said to them, “No, my brothers, do not act so #underline[wickedly]; since this man has come into my house, do not do this #underline[vile] thing. 
#versenum(24) Behold, here are my #underline[virgin] daughter and his concubine. Let me bring them out now. #underline[Violate] them and do with them what seems good to you, but against this man do not do this outrageous thing.” 
#versenum(25) But the men would not listen to him. So the man seized his concubine and made her go out to them. And they knew her and #underline[abused] her all night until the morning. And as the dawn began to break, they let her go. 
#versenum(26) And as morning appeared, the woman came and fell down at the door of the man’s house where her master was, until it was light.


  
#versenum(27) And her master rose up in the morning, and when he opened the doors of the house and went out to go on his way, behold, there was his concubine lying at the door of the house, with her hands on the #underline[threshold]. 
#versenum(28) He said to her, “Get up, let us be going.” But there was no answer. Then he put her on the donkey, and the man rose up and went away to his home. 
#versenum(29) And when he entered his house, he took a #underline[knife], and taking hold of his concubine he divided her, limb by limb, into #mynumber[twelve] pieces, and sent her throughout all the territory of #myhl(placesColor)[Israel]. 
#versenum(30) And all who saw it said, “Such a thing has never happened or been seen from the day that the people of #myname[Israel] came up out of the land of #myhl(placesColor)[Egypt] until this day; consider it, take counsel, and speak.”


  
#chapter-heading[Judges 20]


#section-heading[Israel’s War with the Tribe of Benjamin]


#versenum(1) Then all the people of #myname[Israel] came out, from #myhl(placesColor)[Dan] to #myhl(placesColor)[Beersheba], #underline[including] the land of #myhl(placesColor)[Gilead], and the congregation assembled as one man to the #myhl(divineColor)[#smallcaps[Lord]] at #myhl(placesColor)[Mizpah]. 
#versenum(2) And the chiefs of all the people, of all the tribes of #myname[Israel], presented themselves in the assembly of the people of #myhl(divineColor)[God], #mynumber[400,000] men on foot that drew the sword. 
#versenum(3) (Now the people of #myname[Benjamin] heard that the people of #myname[Israel] had gone up to #myhl(placesColor)[Mizpah].) And the people of #myname[Israel] said, “Tell us, how did this evil #underline[happen]?” 
#versenum(4) And the #myname[Levite], the husband of the woman who was #underline[murdered], answered and said, “I came to #myhl(placesColor)[Gibeah] that belongs to #myname[Benjamin], I and my concubine, to spend the night. 
#versenum(5) And the leaders of #myhl(placesColor)[Gibeah] rose against me and surrounded the house against me by night. They meant to kill me, and they #underline[violated] my concubine, and she is dead. 
#versenum(6) So I took hold of my concubine and cut her in pieces and sent her throughout all the country of the inheritance of #myname[Israel], for they have committed #underline[abomination] and outrage in #myhl(placesColor)[Israel]. 
#versenum(7) Behold, you people of #myname[Israel], all of you, give your #underline[advice] and counsel here.”


  
#versenum(8) And all the people arose as one man, saying, “None of us will go to his tent, and none of us will return to his house. 
#versenum(9) But now this is what we will do to #myhl(placesColor)[Gibeah]: we will go up against it by lot, 
#versenum(10) and we will take #mynumber[ten] men of a #mynumber[hundred] throughout all the tribes of #myname[Israel], and a #mynumber[hundred] of a #mynumber[thousand], and a #mynumber[thousand of ten thousand], to bring provisions for the people, that when they come they may repay #myhl(placesColor)[Gibeah] of #myname[Benjamin] for all the outrage that they have committed in #myhl(placesColor)[Israel].” 
#versenum(11) So all the men of #myname[Israel] gathered against the city, #underline[united] as one man.


  
#versenum(12) And the tribes of #myname[Israel] sent men through all the tribe of #myname[Benjamin], saying, “What evil is this that has taken place among you? 
#versenum(13) Now therefore give up the men, the worthless fellows in #myhl(placesColor)[Gibeah], that we may put them to death and #underline[purge] evil from #myname[Israel].” But the #myname[Benjaminites] would not listen to the voice of their brothers, the people of #myname[Israel]. 
#versenum(14) Then the people of #myname[Benjamin] came together out of the cities to #myhl(placesColor)[Gibeah] to go out to battle against the people of #myname[Israel]. 
#versenum(15) And the people of #myname[Benjamin] mustered out of their cities on that day #underline[#mynumber[26,000]] men who drew the sword, besides the inhabitants of #myhl(placesColor)[Gibeah], who mustered #mynumber[700] chosen men. 
#versenum(16) Among all these were #mynumber[700] chosen men who were left-handed; every one could #underline[sling] a stone at a hair and not #underline[miss]. 
#versenum(17) And the men of #myname[Israel], apart from #myname[Benjamin], mustered #mynumber[400,000] men who drew the sword; all these were men of war.


  
#versenum(18) The people of #myname[Israel] arose and went up to #myhl(placesColor)[Bethel] and inquired of #myhl(divineColor)[God], “Who shall go up #mynumber[first] for us to fight against the people of #myname[Benjamin]?” And the #myhl(divineColor)[#smallcaps[Lord]] said, “#myname[Judah] shall go up #mynumber[first].”


  
#versenum(19) Then the people of #myname[Israel] rose in the morning and encamped against #myhl(placesColor)[Gibeah]. 
#versenum(20) And the men of #myname[Israel] went out to fight against #myname[Benjamin], and the men of #myname[Israel] drew up the battle line against them at #myhl(placesColor)[Gibeah]. 
#versenum(21) The people of #myname[Benjamin] came out of #myhl(placesColor)[Gibeah] and destroyed on that day #mynumber[22,000] men of the #myname[Israelites]. 
#versenum(22) But the people, the men of #myname[Israel], took #underline[courage], and again formed the battle line in the same place where they had formed it on the #mynumber[first] day. 
#versenum(23) And the people of #myname[Israel] went up and wept before the #myhl(divineColor)[#smallcaps[Lord]] until the evening. And they inquired of the #myhl(divineColor)[#smallcaps[Lord]], “Shall we again draw near to fight against our brothers, the people of #myname[Benjamin]?” And the #myhl(divineColor)[#smallcaps[Lord]] said, “Go up against them.”


  
#versenum(24) So the people of #myname[Israel] came near against the people of #myname[Benjamin] the #mynumber[second] day. 
#versenum(25) And #myname[Benjamin] went against them out of #myhl(placesColor)[Gibeah] the #mynumber[second] day, and destroyed #underline[#mynumber[18,000]] men of the people of #myname[Israel]. All these were men who drew the sword. 
#versenum(26) Then all the people of #myname[Israel], the whole army, went up and came to #myhl(placesColor)[Bethel] and wept. They sat there before the #myhl(divineColor)[#smallcaps[Lord]] and #underline[fasted] that day until evening, and offered burnt offerings and peace offerings before the #myhl(divineColor)[#smallcaps[Lord]]. 
#versenum(27) And the people of #myname[Israel] inquired of the #myhl(divineColor)[#smallcaps[Lord]] (for the ark of the covenant of #myhl(divineColor)[God] was there in those days, 
#versenum(28) and #myhl(menColor)[Phinehas] the son of #myhl(menColor)[Eleazar], son of #myhl(menColor)[Aaron], #underline[ministered] before it in those days), saying, “Shall we go out once more to battle against our brothers, the people of #myname[Benjamin], or shall we cease?” And the #myhl(divineColor)[#smallcaps[Lord]] said, “Go up, for tomorrow I will give them into your hand.”


  
#versenum(29) So #myname[Israel] set men in ambush around #myhl(placesColor)[Gibeah]. 
#versenum(30) And the people of #myname[Israel] went up against the people of #myname[Benjamin] on the #mynumber[third] day and set themselves in array against #myhl(placesColor)[Gibeah], as at other times. 
#versenum(31) And the people of #myname[Benjamin] went out against the people and were drawn away from the city. And as at other times they began to strike and kill some of the people in the highways, one of which goes up to #myhl(placesColor)[Bethel] and the other to #myhl(placesColor)[Gibeah], and in the open country, about #mynumber[thirty] men of #myname[Israel]. 
#versenum(32) And the people of #myname[Benjamin] said, “They are routed before us, as at the first.” But the people of #myname[Israel] said, “Let us flee and draw them away from the city to the highways.” 
#versenum(33) And all the men of #myname[Israel] rose up out of their place and set themselves in array at #underline[#myname[Baal-tamar]], and the men of #myname[Israel] who were in ambush rushed out of their place from #underline[#myhl(placesColor)[Maareh-geba]].#footnote[Judges 20:33 Some Septuagint manuscripts #emph[place west of Geba]] 
#versenum(34) And there came against #myhl(placesColor)[Gibeah] #mynumber[10,000] chosen men out of all #myname[Israel], and the battle was hard, but the #myname[Benjaminites] did not know that disaster was close upon them. 
#versenum(35) And the #myhl(divineColor)[#smallcaps[Lord]] defeated #myname[Benjamin] before #myname[Israel], and the people of #myname[Israel] destroyed #underline[#mynumber[25,100]] men of #myname[Benjamin] that day. All these were men who drew the sword. 
#versenum(36) So the people of #myname[Benjamin] saw that they were defeated.


  The men of #myname[Israel] gave ground to #myname[Benjamin], because they #underline[trusted] the men in ambush whom they had set against #myhl(placesColor)[Gibeah]. 
#versenum(37) Then the men in ambush hurried and rushed against #myhl(placesColor)[Gibeah]; the men in ambush moved out and struck all the city with the edge of the sword. 
#versenum(38) Now the appointed signal between the men of #myname[Israel] and the men in the main ambush was that when they made a great #underline[cloud] of smoke rise up out of the city 
#versenum(39) the men of #myname[Israel] should turn in battle. Now #myname[Benjamin] had #underline[begun] to strike and kill about #mynumber[thirty] men of #myname[Israel]. They said, “Surely they are defeated before us, as in the #mynumber[first] battle.” 
#versenum(40) But when the signal began to rise out of the city in a #underline[column] of smoke, the #myname[Benjaminites] looked behind them, and behold, the whole of the city went up in smoke to heaven. 
#versenum(41) Then the men of #myname[Israel] turned, and the men of #myname[Benjamin] were dismayed, for they saw that disaster was close upon them. 
#versenum(42) Therefore they turned their backs before the men of #myname[Israel] in the direction of the wilderness, but the battle overtook them. And those who came out of the cities were #underline[destroying] them in their midst. 
#versenum(43) Surrounding the #myname[Benjaminites], they pursued them and trod them down from #underline[#myhl(placesColor)[Nohah]]#footnote[Judges 20:43 Septuagint; Hebrew \[at their\] #emph[resting place]] as far as opposite #myhl(placesColor)[Gibeah] on the east. 
#versenum(44) #mynumber[Eighteen thousand] men of #myname[Benjamin] fell, all of them men of valor. 
#versenum(45) And they turned and fled toward the wilderness to the #myhl(placesColor)[rock of Rimmon]. #mynumber[Five thousand] men of them were cut down in the highways. And they were pursued hard to #underline[#myhl(placesColor)[Gidom]], and #mynumber[2,000] men of them were struck down. 
#versenum(46) So all who fell that day of #myname[Benjamin] were #underline[#mynumber[25,000]] men who drew the sword, all of them men of valor. 
#versenum(47) But #mynumber[600] men turned and fled toward the wilderness to the #myhl(placesColor)[rock of Rimmon] and remained at the #myhl(placesColor)[rock of Rimmon] #mynumber[four] months. 
#versenum(48) And the men of #myname[Israel] turned back against the people of #myname[Benjamin] and struck them with the edge of the sword, the city, men and #underline[beasts] and all that they found. And all the towns that they found they set on fire.


  
#chapter-heading[Judges 21]


#section-heading[Wives Provided for the Tribe of Benjamin]


#versenum(1) Now the men of #myname[Israel] had sworn at #myhl(placesColor)[Mizpah], “No one of us shall give his daughter in marriage to #myname[Benjamin].” 
#versenum(2) And the people came to #myhl(placesColor)[Bethel] and sat there till evening before #myhl(divineColor)[God], and they lifted up their voices and wept bitterly. 
#versenum(3) And they said, “O #myhl(divineColor)[#smallcaps[Lord], the God of Israel], why has this happened in #myhl(placesColor)[Israel], that today there should be one tribe lacking in #myhl(placesColor)[Israel]?” 
#versenum(4) And the next day the people rose early and built there an altar and offered burnt offerings and peace offerings. 
#versenum(5) And the people of #myname[Israel] said, “Which of all the tribes of #myname[Israel] did not come up in the assembly to the #myhl(divineColor)[#smallcaps[Lord]]?” For they had taken a great oath concerning him who did not come up to the #myhl(divineColor)[#smallcaps[Lord]] to #myhl(placesColor)[Mizpah], saying, “He shall surely be put to death.” 
#versenum(6) And the people of #myname[Israel] had compassion for #myname[Benjamin] their brother and said, “One tribe is cut off from #myname[Israel] this day. 
#versenum(7) What shall we do for wives for those who are left, since we have sworn by the #myhl(divineColor)[#smallcaps[Lord]] that we will not give them any of our daughters for wives?”


  
#versenum(8) And they said, “What one is there of the tribes of #myname[Israel] that did not come up to the #myhl(divineColor)[#smallcaps[Lord]] to #myhl(placesColor)[Mizpah]?” And behold, no one had come to the camp from #myhl(placesColor)[Jabesh-gilead], to the assembly. 
#versenum(9) For when the people were mustered, behold, not one of the inhabitants of #myhl(placesColor)[Jabesh-gilead] was there. 
#versenum(10) So the congregation sent #mynumber[12,000] of their #underline[bravest] men there and commanded them, “Go and strike the inhabitants of #myhl(placesColor)[Jabesh-gilead] with the edge of the sword; also the women and the little ones. 
#versenum(11) This is what you shall do: every male and every woman that has #underline[lain] with a male you shall #underline[devote] to destruction.” 
#versenum(12) And they found among the inhabitants of #myhl(placesColor)[Jabesh-gilead] #underline[#mynumber[400]] young #underline[virgins] who had not known a man by lying with him, and they brought them to the camp at #myhl(placesColor)[Shiloh], which is in the land of #myhl(placesColor)[Canaan].


  
#versenum(13) Then the whole congregation sent word to the people of #myname[Benjamin] who were at the #myhl(placesColor)[rock of Rimmon] and #underline[proclaimed] peace to them. 
#versenum(14) And #myname[Benjamin] returned at that time. And they gave them the women whom they had saved alive of the women of #myhl(placesColor)[Jabesh-gilead], but they were not enough for them. 
#versenum(15) And the people had compassion on #myname[Benjamin] because the #myhl(divineColor)[#smallcaps[Lord]] had made a breach in the tribes of #myname[Israel].


  
#versenum(16) Then the elders of the congregation said, “What shall we do for wives for those who are left, since the women are destroyed out of #myname[Benjamin]?” 
#versenum(17) And they said, “There must be an inheritance for the #underline[survivors] of #myname[Benjamin], that a tribe not be #underline[blotted] out from #myname[Israel]. 
#versenum(18) Yet we cannot give them wives from our daughters.” For the people of #myname[Israel] had sworn, “Cursed be he who gives a wife to #myname[Benjamin].” 
#versenum(19) So they said, “Behold, there is the #underline[yearly] feast of the #myhl(divineColor)[#smallcaps[Lord]] at #myhl(placesColor)[Shiloh], which is north of #myhl(placesColor)[Bethel], on the east of the #underline[highway] that goes up from #myhl(placesColor)[Bethel] to #myhl(placesColor)[Shechem], and south of #underline[#myhl(placesColor)[Lebonah]].” 
#versenum(20) And they commanded the people of #myname[Benjamin], saying, “Go and lie in ambush in the vineyards 
#versenum(21) and watch. If the daughters of #myhl(placesColor)[Shiloh] come out to #underline[dance] in the dances, then come out of the vineyards and #underline[snatch] each man his wife from the daughters of #myhl(placesColor)[Shiloh], and go to the land of #myname[Benjamin]. 
#versenum(22) And when their fathers or their brothers come to #underline[complain] to us, we will say to them, ‘Grant them #underline[graciously] to us, because we did not take for each man of them his wife in battle, neither did you give them to them, #underline[else] you would now be #underline[guilty].’” 
#versenum(23) And the people of #myname[Benjamin] did so and took their wives, according to their number, from the #underline[dancers] whom they carried off. Then they went and returned to their inheritance and rebuilt the towns and lived in them. 
#versenum(24) And the people of #myname[Israel] departed from there at that time, every man to his tribe and family, and they went out from there every man to his inheritance.


  
#versenum(25) In those days there was no king in #myhl(placesColor)[Israel]. Everyone did what was right in his own eyes.


  
#chapter-heading[Ruth 1]


#section-heading[Naomi Widowed]


#versenum(1) In the days when the judges ruled there was a #underline[famine] in the land, and a man of #myhl(placesColor)[Bethlehem in Judah] went to sojourn in the country of #myhl(placesColor)[Moab], he and his wife and his #mynumber[two] sons. 
#versenum(2) The name of the man was #myhl(menColor)[Elimelech] and the name of his wife #myhl(womenColor)[Naomi], and the names of his #mynumber[two] sons were #myhl(menColor)[Mahlon] and #myhl(menColor)[Chilion]. They were #underline[#myname[Ephrathites]] from #myhl(placesColor)[Bethlehem in Judah]. They went into the country of #myhl(placesColor)[Moab] and remained there. 
#versenum(3) But #myhl(menColor)[Elimelech], the husband of #myhl(womenColor)[Naomi], died, and she was left with her #mynumber[two] sons. 
#versenum(4) These took #myname[Moabite] wives; the name of the one was #myhl(womenColor)[Orpah] and the name of the other #myhl(womenColor)[Ruth]. They lived there about #mynumber[ten] years, 
#versenum(5) and both #myhl(menColor)[Mahlon] and #myhl(menColor)[Chilion] died, so that the woman was left without her #mynumber[two] sons and her husband.


  
#section-heading[Ruth’s Loyalty to Naomi]


#versenum(6) Then she arose with her daughters-in-law to return from the country of #myhl(placesColor)[Moab], for she had heard in the fields of #myhl(placesColor)[Moab] that the #myhl(divineColor)[#smallcaps[Lord]] had #underline[visited] his people and given them food. 
#versenum(7) So she set out from the place where she was with her #mynumber[two] daughters-in-law, and they went on the way to return to the land of #myname[Judah]. 
#versenum(8) But #myhl(womenColor)[Naomi] said to her #mynumber[two] daughters-in-law, “Go, return each of you to her mother’s house. May the #myhl(divineColor)[#smallcaps[Lord]] deal kindly with you, as you have dealt with the dead and with me. 
#versenum(9) The #myhl(divineColor)[#smallcaps[Lord]] grant that you may find rest, each of you in the house of her husband!” Then she kissed them, and they lifted up their voices and wept. 
#versenum(10) And they said to her, “No, we will return with you to your people.” 
#versenum(11) But #myhl(womenColor)[Naomi] said, “Turn back, my daughters; why will you go with me? Have I yet sons in my womb that they may become your #underline[husbands]? 
#versenum(12) Turn back, my daughters; go your way, for I am too old to have a husband. If I should say I have #underline[hope], even if I should have a husband this night and should bear sons, 
#versenum(13) would you therefore wait till they were #underline[grown]? Would you therefore #underline[refrain] from #underline[marrying]? No, my daughters, for it is #underline[exceedingly] #underline[bitter] to me for your sake that the hand of the #myhl(divineColor)[#smallcaps[Lord]] has gone out against me.” 
#versenum(14) Then they lifted up their voices and wept again. And #myhl(womenColor)[Orpah] kissed her mother-in-law, but #myhl(womenColor)[Ruth] #underline[clung] to her.


  
#versenum(15) And she said, “See, your sister-in-law has gone back to her people and to her gods; return after your sister-in-law.” 
#versenum(16) But #myhl(womenColor)[Ruth] said, “Do not #underline[urge] me to leave you or to return from following you. For where you go I will go, and where you lodge I will lodge. Your people shall be my people, and your #myhl(divineColor)[God] my #myhl(divineColor)[God]. 
#versenum(17) Where you die I will die, and there will I be buried. May the #myhl(divineColor)[#smallcaps[Lord]] do so to me and more also if anything but death parts me from you.” 
#versenum(18) And when #myhl(womenColor)[Naomi] saw that she was #underline[determined] to go with her, she said no more.


  
#section-heading[Naomi and Ruth Return]


#versenum(19) So the #mynumber[two] of them went on until they came to #myhl(placesColor)[Bethlehem]. And when they came to #myhl(placesColor)[Bethlehem], the whole town was #underline[stirred] because of them. And the women said, “Is this #myhl(womenColor)[Naomi]?” 
#versenum(20) She said to them, “Do not call me #myhl(womenColor)[Naomi];#footnote[Ruth 1:20 #emph[Naomi] means #emph[pleasant]] call me #underline[Mara],#footnote[Ruth 1:20 #emph[Mara] means #emph[bitter]] for the Almighty has dealt very bitterly with me. 
#versenum(21) I went away full, and the #myhl(divineColor)[#smallcaps[Lord]] has brought me back empty. Why call me #myhl(womenColor)[Naomi], when the #myhl(divineColor)[#smallcaps[Lord]] has #underline[testified] against me and the Almighty has brought #underline[calamity] upon me?”


  
#versenum(22) So #myhl(womenColor)[Naomi] returned, and #myhl(womenColor)[Ruth] the #myname[Moabite] her daughter-in-law with her, who returned from the country of #myhl(placesColor)[Moab]. And they came to #myhl(placesColor)[Bethlehem] at the beginning of barley harvest.


  
#chapter-heading[Ruth 2]


#section-heading[Ruth Meets Boaz]


#versenum(1) Now #myhl(womenColor)[Naomi] had a relative of her #underline[husband’s], a worthy man of the clan of #myhl(menColor)[Elimelech], whose name was #myhl(menColor)[Boaz]. 
#versenum(2) And #myhl(womenColor)[Ruth] the #myname[Moabite] said to #myhl(womenColor)[Naomi], “Let me go to the field and glean among the ears of grain after him in whose sight I shall find favor.” And she said to her, “Go, my daughter.” 
#versenum(3) So she set out and went and gleaned in the field after the reapers, and she happened to come to the part of the field belonging to #myhl(menColor)[Boaz], who was of the clan of #myhl(menColor)[Elimelech]. 
#versenum(4) And behold, #myhl(menColor)[Boaz] came from #myhl(placesColor)[Bethlehem]. And he said to the reapers, “The #myhl(divineColor)[#smallcaps[Lord]] be with you!” And they answered, “The #myhl(divineColor)[#smallcaps[Lord]] bless you.” 
#versenum(5) Then #myhl(menColor)[Boaz] said to his young man who was in charge of the reapers, “Whose young woman is this?” 
#versenum(6) And the servant who was in charge of the reapers answered, “She is the young #myname[Moabite] woman, who came back with #myhl(womenColor)[Naomi] from the country of #myhl(placesColor)[Moab]. 
#versenum(7) She said, ‘Please let me glean and gather among the sheaves after the reapers.’ So she came, and she has #underline[continued] from early morning until now, except for a #underline[short] rest.”#footnote[Ruth 2:7 Compare Septuagint, Vulgate; the meaning of the Hebrew phrase is uncertain]


  
#versenum(8) Then #myhl(menColor)[Boaz] said to #myhl(womenColor)[Ruth], “Now, listen, my daughter, do not go to glean in another field or leave this one, but keep close to my young women. 
#versenum(9) Let your eyes be on the field that they are #underline[reaping], and go after them. Have I not charged the young men not to touch you? And when you are thirsty, go to the vessels and drink what the young men have drawn.” 
#versenum(10) Then she fell on her face, bowing to the ground, and said to him, “Why have I found favor in your eyes, that you should take notice of me, since I am a #underline[foreigner]?” 
#versenum(11) But #myhl(menColor)[Boaz] answered her, “All that you have done for your mother-in-law since the death of your husband has been #underline[fully] told to me, and how you left your father and mother and your native land and came to a people that you did not know before. 
#versenum(12) The #myhl(divineColor)[#smallcaps[Lord]] repay you for what you have done, and a full #underline[reward] be given you by the #myhl(divineColor)[#smallcaps[Lord], the God of Israel], under whose wings you have come to take refuge!” 
#versenum(13) Then she said, “I have found favor in your eyes, my lord, for you have #underline[comforted] me and spoken kindly to your servant, though I am not one of your servants.”


  
#versenum(14) And at #underline[mealtime] #myhl(menColor)[Boaz] said to her, “Come here and eat some bread and #underline[dip] your morsel in the wine.” So she sat beside the reapers, and he passed to her #underline[roasted] grain. And she ate until she was satisfied, and she had some left over. 
#versenum(15) When she rose to glean, #myhl(menColor)[Boaz] #underline[instructed] his young men, saying, “Let her glean even among the sheaves, and do not reproach her. 
#versenum(16) And also pull out some from the #underline[bundles] for her and leave it for her to glean, and do not #underline[rebuke] her.”


  
#versenum(17) So she gleaned in the field until evening. Then she beat out what she had gleaned, and it was about an ephah#footnote[Ruth 2:17 An #emph[ephah] was about 3/5 bushel or 22 liters] of barley. 
#versenum(18) And she took it up and went into the city. Her mother-in-law saw what she had gleaned. She also brought out and gave her what food she had left over after being satisfied. 
#versenum(19) And her mother-in-law said to her, “Where did you glean today? And where have you worked? Blessed be the man who took notice of you.” So she told her mother-in-law with whom she had worked and said, “The man’s name with whom I worked today is #myhl(menColor)[Boaz].” 
#versenum(20) And #myhl(womenColor)[Naomi] said to her daughter-in-law, “May he be blessed by the #myhl(divineColor)[#smallcaps[Lord]], whose kindness has not forsaken the living or the dead!” #myhl(womenColor)[Naomi] also said to her, “The man is a close relative of ours, one of our #underline[redeemers].” 
#versenum(21) And #myhl(womenColor)[Ruth] the #myname[Moabite] said, “Besides, he said to me, ‘You shall keep close by my young men until they have finished all my harvest.’” 
#versenum(22) And #myhl(womenColor)[Naomi] said to #myhl(womenColor)[Ruth], her daughter-in-law, “It is good, my daughter, that you go out with his young women, lest in another field you be #underline[assaulted].” 
#versenum(23) So she kept close to the young women of #myhl(menColor)[Boaz], gleaning until the end of the barley and wheat #underline[harvests]. And she lived with her mother-in-law.


  
#chapter-heading[Ruth 3]


#section-heading[Ruth and Boaz at the Threshing Floor]


#versenum(1) Then #myhl(womenColor)[Naomi] her mother-in-law said to her, “My daughter, should I not #underline[seek] rest for you, that it may be well with you? 
#versenum(2) Is not #myhl(menColor)[Boaz] our relative, with whose young women you were? See, he is #underline[winnowing] barley tonight at the threshing floor. 
#versenum(3) #underline[Wash] therefore and anoint yourself, and put on your cloak and go down to the threshing floor, but do not make yourself known to the man until he has finished eating and #underline[drinking]. 
#versenum(4) But when he lies down, observe the place where he lies. Then go and #underline[uncover] his feet and lie down, and he will tell you what to do.” 
#versenum(5) And she replied, “All that you say I will do.”


  
#versenum(6) So she went down to the threshing floor and did just as her mother-in-law had commanded her. 
#versenum(7) And when #myhl(menColor)[Boaz] had #underline[eaten] and #underline[drunk], and his heart was merry, he went to lie down at the end of the heap of grain. Then she came softly and #underline[uncovered] his feet and lay down. 
#versenum(8) At midnight the man was #underline[startled] and turned over, and behold, a woman lay at his feet! 
#versenum(9) He said, “Who are you?” And she answered, “I am #myhl(womenColor)[Ruth], your servant. Spread your wings#footnote[Ruth 3:9 Compare 2:12; the word for #emph[wings] can also mean #emph[corners of a garment]] over your servant, for you are a redeemer.” 
#versenum(10) And he said, “May you be blessed by the #myhl(divineColor)[#smallcaps[Lord]], my daughter. You have made this last kindness greater than the #mynumber[first] in that you have not gone after young men, whether #underline[poor] or rich. 
#versenum(11) And now, my daughter, do not fear. I will do for you all that you ask, for all my #underline[fellow] #underline[townsmen] know that you are a worthy woman. 
#versenum(12) And now it is true that I am a redeemer. Yet there is a redeemer #underline[nearer] than I. 
#versenum(13) Remain tonight, and in the morning, if he will redeem you, good; let him do it. But if he is not #underline[willing] to redeem you, then, as the #myhl(divineColor)[#smallcaps[Lord]] lives, I will redeem you. Lie down until the morning.”


  
#versenum(14) So she lay at his feet until the morning, but arose before one could #underline[recognize] another. And he said, “Let it not be known that the woman came to the threshing floor.” 
#versenum(15) And he said, “Bring the #underline[garment] you are #underline[wearing] and hold it out.” So she held it, and he #underline[measured] out #mynumber[six] measures of barley and put it on her. Then she went into the city. 
#versenum(16) And when she came to her mother-in-law, she said, “How did you #underline[fare], my daughter?” Then she told her all that the man had done for her, 
#versenum(17) saying, “These #mynumber[six] measures of barley he gave to me, for he said to me, ‘You must not go back #underline[empty-handed] to your mother-in-law.’” 
#versenum(18) She replied, “Wait, my daughter, until you #underline[learn] how the matter turns out, for the man will not rest but will #underline[settle] the matter today.”


  
#chapter-heading[Ruth 4]


#section-heading[Boaz Redeems Ruth]


#versenum(1) Now #myhl(menColor)[Boaz] had gone up to the gate and sat down there. And behold, the redeemer, of whom #myhl(menColor)[Boaz] had spoken, came by. So #myhl(menColor)[Boaz] said, “Turn aside, #underline[friend]; sit down here.” And he turned aside and sat down. 
#versenum(2) And he took #mynumber[ten] men of the elders of the city and said, “Sit down here.” So they sat down. 
#versenum(3) Then he said to the redeemer, “#myhl(womenColor)[Naomi], who has come back from the country of #myhl(placesColor)[Moab], is #underline[selling] the #underline[parcel] of land that belonged to our relative #myhl(menColor)[Elimelech]. 
#versenum(4) So I thought I would tell you of it and say, ‘Buy it in the presence of those sitting here and in the presence of the elders of my people.’ If you will redeem it, redeem it. But if you#footnote[Ruth 4:4 Hebrew #emph[he]] will not, tell me, that I may know, for there is no one besides you to redeem it, and I come after you.” And he said, “I will redeem it.” 
#versenum(5) Then #myhl(menColor)[Boaz] said, “The day you buy the field from the hand of #myhl(womenColor)[Naomi], you also #underline[acquire] #myhl(womenColor)[Ruth]#footnote[Ruth 4:5 Masoretic Text #emph[you also buy it from Ruth]] the #myname[Moabite], the widow of the dead, in order to perpetuate the name of the dead in his inheritance.” 
#versenum(6) Then the redeemer said, “I cannot redeem it for myself, lest I #underline[impair] my own inheritance. Take my right of #underline[redemption] yourself, for I cannot redeem it.”


  
#versenum(7) Now this was the custom in #underline[former] times in #myhl(placesColor)[Israel] concerning #underline[redeeming] and #underline[exchanging]: to #underline[confirm] a #underline[transaction], the one drew off his sandal and gave it to the other, and this was the manner of #underline[attesting] in #myhl(placesColor)[Israel]. 
#versenum(8) So when the redeemer said to #myhl(menColor)[Boaz], “Buy it for yourself,” he drew off his sandal. 
#versenum(9) Then #myhl(menColor)[Boaz] said to the elders and all the people, “You are witnesses this day that I have bought from the hand of #myhl(womenColor)[Naomi] all that belonged to #myhl(menColor)[Elimelech] and all that belonged to #myhl(menColor)[Chilion] and to #myhl(menColor)[Mahlon]. 
#versenum(10) Also #myhl(womenColor)[Ruth] the #myname[Moabite], the widow of #myhl(menColor)[Mahlon], I have bought to be my wife, to perpetuate the name of the dead in his inheritance, that the name of the dead may not be cut off from among his brothers and from the gate of his native place. You are witnesses this day.” 
#versenum(11) Then all the people who were at the gate and the elders said, “We are witnesses. May the #myhl(divineColor)[#smallcaps[Lord]] make the woman, who is coming into your house, like #underline[#myhl(womenColor)[Rachel]] and #underline[#myhl(womenColor)[Leah]], who together built up the house of #myname[Israel]. May you act #underline[worthily] in #underline[#myhl(placesColor)[Ephrathah]] and be renowned in #myhl(placesColor)[Bethlehem], 
#versenum(12) and may your house be like the house of #myhl(menColor)[Perez], whom #underline[#myhl(womenColor)[Tamar]] bore to #myname[Judah], because of the offspring that the #myhl(divineColor)[#smallcaps[Lord]] will give you by this young woman.”


  
#section-heading[Ruth and Boaz Marry]


#versenum(13) So #myhl(menColor)[Boaz] took #myhl(womenColor)[Ruth], and she became his wife. And he went in to her, and the #myhl(divineColor)[#smallcaps[Lord]] gave her #underline[conception], and she bore a son. 
#versenum(14) Then the women said to #myhl(womenColor)[Naomi], “Blessed be the #myhl(divineColor)[#smallcaps[Lord]], who has not left you this day without a redeemer, and may his name be renowned in #myhl(placesColor)[Israel]! 
#versenum(15) He shall be to you a #underline[restorer] of life and a #underline[nourisher] of your old age, for your daughter-in-law who #underline[loves] you, who is more to you than #mynumber[seven] sons, has given #underline[birth] to him.” 
#versenum(16) Then #myhl(womenColor)[Naomi] took the child and laid him on her #underline[lap] and became his #underline[nurse]. 
#versenum(17) And the women of the neighborhood gave him a name, saying, “A son has been born to #myhl(womenColor)[Naomi].” They named him #myhl(menColor)[Obed]. He was the father of #myhl(menColor)[Jesse], the father of #myhl(menColor)[David].


  
#section-heading[The Genealogy of David]


#versenum(18) Now these are the generations of #myhl(menColor)[Perez]: #myhl(menColor)[Perez] fathered #myhl(menColor)[Hezron], 
#versenum(19) #myhl(menColor)[Hezron] fathered #myhl(menColor)[Ram], #myhl(menColor)[Ram] fathered #myhl(menColor)[Amminadab], 
#versenum(20) #myhl(menColor)[Amminadab] fathered #myhl(menColor)[Nahshon], #myhl(menColor)[Nahshon] fathered #myhl(menColor)[Salmon], 
#versenum(21) #myhl(menColor)[Salmon] fathered #myhl(menColor)[Boaz], #myhl(menColor)[Boaz] fathered #myhl(menColor)[Obed], 
#versenum(22) #myhl(menColor)[Obed] fathered #myhl(menColor)[Jesse], and #myhl(menColor)[Jesse] fathered #myhl(menColor)[David].



#v(1em)
#align(center)[
  #set text(size: 0.85em)
  Taken from the _ESV® Bible_ (_The Holy Bible, English Standard Version®_),
  Copyright © 2001 by Crossway, a publishing ministry of Good News Publishers.
  Used by permission. All rights reserved.
]
