%disk%:
cd %dir%
tesseract temp/%filename% temp/%filename% -l chi_sim --oem 0 pdf
echo success
exit 0