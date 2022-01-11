library(psych)
library(corrplot)
mydata <- read.csv(file.choose(), header = TRUE)
cormatrixp<-corr.test(x=mydata, y =mydata, use = "pairwise",method="spearman",adjust="holm", alpha=.05,ci=TRUE,minlength=5)
cormatrix<-cormatrixp$r
col <- colorRampPalette(c("#4477AA","#77AADD","#FFFFFF","#EE9988","#BB4444"))
pmatrix=cormatrixp$p
corrplot(cormatrix, p.mat = pmatrix, diag=FALSE, sig.level = .05,insig = "pch", method = "color", col = col(200), type = "upper", number.cex = .7, addCoef.col = "black", tl.col = "black", tl.srt = 45)